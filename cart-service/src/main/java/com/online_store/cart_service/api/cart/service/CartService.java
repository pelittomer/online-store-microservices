package com.online_store.cart_service.api.cart.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.online_store.cart_service.api.cart.dto.request.CartRequest;
import com.online_store.cart_service.api.cart.dto.request.UpdateCartRequest;
import com.online_store.cart_service.api.cart.dto.response.CartItemResponse;
import com.online_store.cart_service.api.cart.dto.response.CartResponse;
import com.online_store.cart_service.api.cart.dto.response.ProductResponse;
import com.online_store.cart_service.api.cart.exception.CartItemNotFoundException;
import com.online_store.cart_service.api.cart.exception.InsufficientStockException;
import com.online_store.cart_service.api.cart.model.Cart;
import com.online_store.cart_service.api.cart.model.CartItem;
import com.online_store.cart_service.api.cart.repository.CartItemRepository;
import com.online_store.cart_service.api.cart.repository.CartRepository;
import com.online_store.cart_service.client.product.ProductStockInfo;
import com.online_store.cart_service.common.utils.UtilsService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final UtilsService utilsService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(UtilsService utilsService,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository) {
        this.utilsService = utilsService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public String addProductToCart(CartRequest dto) {
        Long user = utilsService.getCurrentUserId();
        logger.info("User {} attempting to add product {} to cart with quantity {}", user, dto.product(),
                dto.quantity());

        ProductStockInfo productStockInfo = utilsService.getProductStock(dto.product(), dto.productStock());
        validateQuantityAgainstStock(dto.quantity(), productStockInfo.stockQuantity());

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    logger.info("Cart not found for user {}. Creating a new cart.", user);
                    return cartRepository.save(createCartMapper(user));
                });

        Optional<CartItem> existingCartItem = findExistingCartItem(cart, dto.productStock());

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            Integer newQuantity = dto.quantity() + cartItem.getQuantity();
            validateQuantityAgainstStock(newQuantity, productStockInfo.stockQuantity());

            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
            logger.info("Updated quantity of product stock {} in cart of user {} to {}", dto.productStock(), user,
                    newQuantity);
            return "Product quantity updated in cart.";
        } else {
            CartItem newCartItem = createCartItemMapper(
                    cart,
                    dto.productStock(),
                    dto.product(),
                    dto.quantity());

            cart.getCartItems().add(newCartItem);
            cartRepository.save(cart);

            logger.info("Added new product stock {} to cart of user {}.", dto.productStock(), user);
            return "Product added to cart.";
        }
    }

    public String removeProductFromCart(Long cartItemId) {
        Long user = utilsService.getCurrentUserId();
        logger.info("User {} attempting to remove cart item with ID {}", user, cartItemId);

        Cart cart = getCartByUser(user);

        Boolean removed = cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));

        if (!removed) {
            logger.warn("Cart item {} not found in cart of user {}.", cartItemId, user);
            throw new EntityNotFoundException("Cart item not found in your cart with ID: " + cartItemId);
        }
        cartRepository.save(cart);
        logger.info("Cart item {} successfully removed from cart of user {}.", cartItemId, user);
        return "Product removed from cart successfully.";
    }

    public String clearAllCartItems() {
        Long user = utilsService.getCurrentUserId();
        logger.info("Attempting to clear all cart items for user {}", user);

        Cart cart = getCartByUser(user);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        logger.info("Cart successfully cleared for user {}", user);
        return "Cart clear.";
    }

    public CartResponse listCartItems() {
        Long user = utilsService.getCurrentUserId();
        logger.info("Listing all cart items for user {}", user);

        Cart cart = getCartByUser(user);
        return cartResponseMapper(cart);
    }

    public String updateCartItem(UpdateCartRequest dto) {
        Long user = utilsService.getCurrentUserId();
        logger.info("User {} attempting to update cart item {} to quantity {}", user, dto.id(), dto.quantity());

        Cart cart = getCartByUser(user);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(dto.id()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("Cart item {} not found in cart of user {}.", dto.id(), user);
                    return new CartItemNotFoundException("Cart item not found in your cart with ID: " + dto.id());
                });

        ProductStockInfo productStockInfo = utilsService.getProductStock(cartItem.getProduct(),
                cartItem.getProductStock());
        validateQuantityAgainstStock(dto.quantity(), productStockInfo.stockQuantity());

        cartItem.setQuantity(dto.quantity());
        cartItemRepository.save(cartItem);
        
        logger.info("Cart item {} updated successfully to quantity {} for user {}.", dto.id(), dto.quantity(), user);
        return "Cart item updated successfully.";
    }

    public Cart getCartByUser(Long user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found for the current user."));
    }

    public Cart createCartMapper(Long userId) {
        return new Cart(userId);
    }

    public CartItem createCartItemMapper(Cart cart,
            Long productStock,
            Long product,
            Integer quantity) {
        return new CartItem(
                quantity,
                product,
                productStock,
                cart);
    }

    public CartResponse cartResponseMapper(Cart dto) {
        List<CartItemResponse> cartItems = dto.getCartItems().stream()
                .map(this::cartItemResponseMapper).toList();
        return new CartResponse(
                dto.getId(),
                cartItems,
                dto.getCreatedAt(),
                dto.getUpdatedAt());
    }

    public CartItemResponse cartItemResponseMapper(CartItem dto) {
        ProductResponse product = utilsService.getProductResponse(dto.getProduct());
        return new CartItemResponse(
                dto.getId(),
                dto.getQuantity(),
                product,
                dto.getProductStock());
    }

    private void validateQuantityAgainstStock(Integer requestedQuantity, Integer availableStock) {
        if (requestedQuantity > availableStock) {
            logger.error("Requested quantity {} exceeds available stock {}.", requestedQuantity, availableStock);
            throw new InsufficientStockException("Requested quantity exceeds available stock of " + availableStock);
        }
    }

    private Optional<CartItem> findExistingCartItem(Cart cart, Long productStockId) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProductStock().equals(productStockId))
                .findFirst();
    }
}
