package com.online_store.product_service.api.product.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.online_store.product_service.api.brand.dto.BrandResponse;
import com.online_store.product_service.api.brand.model.Brand;
import com.online_store.product_service.api.brand.service.BrandService;
import com.online_store.product_service.api.category.dto.CategoryResponse;
import com.online_store.product_service.api.category.model.Category;
import com.online_store.product_service.api.category.service.CategoryService;
import com.online_store.product_service.api.product.dto.base.DiscountDto;
import com.online_store.product_service.api.product.dto.base.FeatureDto;
import com.online_store.product_service.api.product.dto.request.CriteriaOptionRequest;
import com.online_store.product_service.api.product.dto.request.ProductCriteriaRequest;
import com.online_store.product_service.api.product.dto.request.ProductDetailRequest;
import com.online_store.product_service.api.product.dto.request.ProductRequest;
import com.online_store.product_service.api.product.dto.request.ProductStockRequest;
import com.online_store.product_service.api.product.dto.request.StockVariationRequest;
import com.online_store.product_service.api.product.dto.response.CriteriaOptionResponse;
import com.online_store.product_service.api.product.dto.response.ProductCriteriaResponse;
import com.online_store.product_service.api.product.dto.response.ProductDetailsDetailResponse;
import com.online_store.product_service.api.product.dto.response.ProductDetailsResponse;
import com.online_store.product_service.api.product.dto.response.ProductResponse;
import com.online_store.product_service.api.product.dto.response.ProductStockInfo;
import com.online_store.product_service.api.product.dto.response.ProductStockResponse;
import com.online_store.product_service.api.product.dto.response.ProductVariationOptionResponse;
import com.online_store.product_service.api.product.dto.response.ProductVariationResponse;
import com.online_store.product_service.api.product.dto.response.StockVariationResponse;
import com.online_store.product_service.api.product.model.CriteriaOption;
import com.online_store.product_service.api.product.model.Product;
import com.online_store.product_service.api.product.model.ProductCriteria;
import com.online_store.product_service.api.product.model.ProductDetail;
import com.online_store.product_service.api.product.model.ProductStock;
import com.online_store.product_service.api.product.model.embeddables.Discount;
import com.online_store.product_service.api.product.model.embeddables.Feature;
import com.online_store.product_service.api.product.model.embeddables.StockVariation;
import com.online_store.product_service.api.product.repository.ProductRepository;
import com.online_store.product_service.api.product.repository.ProductStockRepository;
import com.online_store.product_service.api.variation.dto.VariationOptionResponse;
import com.online_store.product_service.api.variation.model.Variation;
import com.online_store.product_service.api.variation.model.VariationOption;
import com.online_store.product_service.api.variation.service.VariationService;
import com.online_store.product_service.client.logistic.CompanyDetailsResponse;
import com.online_store.product_service.client.logistic.CompanyResponse;
import com.online_store.product_service.client.logistic.CompanyStatus;
import com.online_store.product_service.client.logistic.ShipperResponse;
import com.online_store.product_service.client.upload.UploadClient;
import com.online_store.product_service.client.upload.UploadResponse;
import com.online_store.product_service.common.response.ApiResponse;
import com.online_store.product_service.common.utils.UtilsService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
        private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

        private final ProductRepository repository;
        private final ProductStockRepository productStockRepository;
        private final VariationService variationService;
        private final BrandService brandService;
        private final CategoryService categoryService;
        private final UploadClient uploadClient;
        private final UtilsService utilsService;

        public ProductService(ProductRepository repository,
                        ProductStockRepository productStockRepository,
                        VariationService variationService,
                        BrandService brandService,
                        CategoryService categoryService,
                        UploadClient uploadService,
                        UtilsService utilsService) {
                this.repository = repository;
                this.productStockRepository = productStockRepository;
                this.variationService = variationService;
                this.brandService = brandService;
                this.categoryService = categoryService;
                this.uploadClient = uploadService;
                this.utilsService = utilsService;
        }

        public String addProduct(ProductRequest dto,
                        MultipartHttpServletRequest request) {
                CompanyDetailsResponse company = utilsService.getMyCompany();
                Long shipper = utilsService.getShipperById(dto.shipper());
                Brand brand = brandService.findByIdBrand(dto.brand());
                Category category = categoryService.findCategoryById(dto.category());

                if (company.status() != CompanyStatus.APPROVED) {
                        logger.warn("Attempt to add product by a non-approved company: {}", company.name());
                        throw new Error("Your company is not approved. Products can't be added.");
                }

                Map<String, List<MultipartFile>> dynamicFiles = processDynamicFiles(request);
                Product product = productMapper(
                                dto,
                                dynamicFiles,
                                company.id(),
                                shipper,
                                brand,
                                category);
                repository.save(product);

                logger.info("Product '{}' successfully added by company '{}'.", product, company.id());
                return "Product created successfully.";

        }

        public String updateProduct() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
        }

        public List<ProductResponse> listProducts() {
                return repository.findAll().stream()
                                .map(this::productResponseMapper).toList();
        }

        public ProductResponse getProduct(Long id) {
                Product product = findProductById(id);
                return productResponseMapper(product);
        }

        public ProductDetailsResponse getProductById(Long id) {
                Product product = findProductById(id);
                return productDetailsResponseMapper(product);
        }

        public ProductStockInfo getProductStock(Long productId, Long stockId) {
                ProductStock productStock = productStockRepository
                                .findProductStockByProductIdAndStockId(productId, stockId)
                                .orElseThrow(() -> new EntityNotFoundException("Stock not found!"));
                return productStockInfoMapper(productStock);
        }

        private ProductStockInfo productStockInfoMapper(ProductStock dto) {
                return new ProductStockInfo(
                                dto.getId(),
                                dto.getStockQuantity(),
                                dto.getAdditionalPrice(),
                                dto.getIsLimited(),
                                dto.getReplenishQuantity());
        }

        private Map<String, List<MultipartFile>> processDynamicFiles(MultipartHttpServletRequest request) {
                Map<String, List<MultipartFile>> dynamicFiles = new HashMap<>();
                Iterator<String> fileNames = request.getFileNames();

                while (fileNames.hasNext()) {
                        String fileName = fileNames.next();
                        List<MultipartFile> filesForThisKey = request.getFiles(fileName);

                        if (filesForThisKey != null && !filesForThisKey.isEmpty()) {
                                List<MultipartFile> actualFiles = filesForThisKey.stream()
                                                .filter(f -> !f.isEmpty())
                                                .toList();

                                if (!actualFiles.isEmpty()) {
                                        dynamicFiles.put(fileName, actualFiles);
                                }
                        }
                }
                return dynamicFiles;
        }

        public Product findProductById(Long id) {
                return repository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Product not found!"));
        }

        public ProductResponse productResponseMapper(Product dto) {
                BrandResponse brand = brandService.brandResponseMapper(dto.getBrand());
                List<Long> images = dto.getImages().stream().toList();
                CompanyResponse company = utilsService.getCompanyById(dto.getCompany());
                return new ProductResponse(
                                dto.getId(),
                                dto.getName(),
                                dto.getPrice(),
                                images,
                                brand,
                                company,
                                dto.getCreatedAt(),
                                dto.getUpdatedAt());
        }

        public Product productMapper(ProductRequest dto,
                        Map<String, List<MultipartFile>> dynamicFiles,
                        Long company,
                        Long shipper,
                        Brand brand,
                        Category category) {
                Set<Long> productImages = dynamicFiles.get("images").stream()
                                .map(this::uploadFile).collect(Collectors.toSet());
                ProductDetail productDetail = productDetailMapper(dto.productDetail(), dynamicFiles);
                List<ProductStock> productStocks = dto.productStocks().stream()
                                .map(this::productStockMapper).collect(Collectors.toList());
                Product product = new Product(
                                dto.name(),
                                dto.price(),
                                dto.isPublished(),
                                productImages,
                                brand,
                                shipper,
                                company,
                                category,
                                productDetail,
                                productStocks);

                productDetail.setProduct(product);
                productStocks.forEach((item) -> item.setProduct(product));
                return product;
        }

        private ProductDetail productDetailMapper(
                        ProductDetailRequest dto,
                        Map<String, List<MultipartFile>> dynamicFiles) {
                Set<Feature> features = dto.features().stream().map(this::featureMapper)
                                .collect(Collectors.toSet());
                List<ProductCriteria> productCriterias = dto.productCriterias().stream()
                                .map((item) -> productCriteriaMapper(item, dynamicFiles)).toList();
                ProductDetail productDetail = new ProductDetail(
                                dto.description(),
                                dto.shortDescription(),
                                features,
                                productCriterias);

                productCriterias.forEach(item -> item.setProductDetail(productDetail));
                return productDetail;
        }

        private Feature featureMapper(FeatureDto dto) {
                return new Feature(
                                dto.name(),
                                dto.value());
        }

        private FeatureDto featureDtoMapper(Feature dto) {
                return new FeatureDto(
                                dto.getName(),
                                dto.getValue());
        }

        private ProductCriteria productCriteriaMapper(ProductCriteriaRequest dto,
                        Map<String, List<MultipartFile>> dynamicFiles) {
                Set<CriteriaOption> criteriaOptions = dto.criteriaOptions().stream()
                                .map((item) -> criteriaOptionMapper(item, dynamicFiles))
                                .collect(Collectors.toSet());
                Variation variation = variationService.findVariationById(dto.variation());

                ProductCriteria productCriteria = new ProductCriteria(
                                variation,
                                criteriaOptions);
                criteriaOptions.forEach(item -> item.setProductCriteria(productCriteria));
                return productCriteria;
        }

        private CriteriaOption criteriaOptionMapper(CriteriaOptionRequest dto,
                        Map<String, List<MultipartFile>> dynamicFiles) {
                VariationOption variationOption = variationService
                                .findVariationOptionById(dto.variationOption());
                Set<Long> images = dynamicFiles.get(dto.variationOption().toString()).stream()
                                .map(this::uploadFile).collect(Collectors.toSet());

                return new CriteriaOption(
                                variationOption,
                                images);
        }

        private ProductStock productStockMapper(ProductStockRequest dto) {
                Set<StockVariation> stockVariations = dto.stockVariations().stream()
                                .map(this::stockVariation).collect(Collectors.toSet());

                return new ProductStock(
                                dto.stockQuantity(),
                                dto.additionalPrice(),
                                dto.isLimited(),
                                dto.replenishQuantity(),
                                stockVariations);

        }

        private StockVariation stockVariation(StockVariationRequest dto) {
                Variation variation = variationService.findVariationById(dto.variation());
                VariationOption variationOption = variationService
                                .findVariationOptionById(dto.variationOption());
                return new StockVariation(
                                variation,
                                variationOption);
        }

        private Long uploadFile(MultipartFile file) {
                ApiResponse<UploadResponse> upload = uploadClient.uploadFile(file);
                return upload.data().upload();
        }

        private ProductDetailsResponse productDetailsResponseMapper(Product dto) {
                DiscountDto discount = null;
                if (dto.getDiscount() != null) {
                        discount = discountMapper(dto.getDiscount());
                }
                List<Long> images = dto.getImages().stream().toList();

                BrandResponse brand = brandService.brandResponseMapper(dto.getBrand());
                ShipperResponse shipper = utilsService.getShipper(dto.getShipper());
                CompanyResponse company = utilsService.getCompanyById(dto.getCompany());
                CategoryResponse category = categoryService.mapCategoryToResponseDto(dto.getCategory());
                ProductDetailsDetailResponse productDetail = productDetailMapper(dto.getProductDetail());
                List<ProductStockResponse> productStocks = dto.getProductStocks().stream()
                                .map(this::productStocksMapper).toList();
                return new ProductDetailsResponse(
                                dto.getId(),
                                dto.getName(),
                                discount,
                                dto.getIsPublished(),
                                images,
                                brand,
                                shipper,
                                company,
                                category,
                                productDetail,
                                productStocks,
                                dto.getCreatedAt(),
                                dto.getUpdatedAt());
        }

        private DiscountDto discountMapper(Discount dto) {
                return new DiscountDto(
                                dto.getDiscountPercentage(),
                                dto.getStartDate(),
                                dto.getEndDate(),
                                dto.getAppliedPrice());
        }

        private ProductDetailsDetailResponse productDetailMapper(ProductDetail dto) {
                List<FeatureDto> features = dto.getFeatures().stream()
                                .map(this::featureDtoMapper).toList();

                List<ProductCriteriaResponse> productCriterias = dto.getProductCriterias().stream()
                                .map(this::productCriteriaMapper).toList();
                return new ProductDetailsDetailResponse(
                                dto.getId(),
                                dto.getDescription(),
                                dto.getShortDescription(),
                                features,
                                productCriterias);
        }

        private ProductCriteriaResponse productCriteriaMapper(ProductCriteria dto) {
                List<CriteriaOptionResponse> criteriaOptions = dto.getCriteriaOptions().stream()
                                .map(this::criteriaOptionsMapper).toList();
                ProductVariationResponse variation = productVariationMapper(dto.getVariation());
                return new ProductCriteriaResponse(
                                dto.getId(),
                                variation,
                                criteriaOptions);
        }

        private ProductVariationResponse productVariationMapper(Variation dto) {
                return new ProductVariationResponse(
                                dto.getId(),
                                dto.getName());
        }

        private CriteriaOptionResponse criteriaOptionsMapper(CriteriaOption dto) {
                List<Long> images = dto.getImages().stream().toList();
                ProductVariationOptionResponse variationOption = variationOptionMapper(dto.getVariationOption());
                return new CriteriaOptionResponse(
                                dto.getId(),
                                variationOption,
                                images);
        }

        private ProductVariationOptionResponse variationOptionMapper(VariationOption dto) {
                return new ProductVariationOptionResponse(
                                dto.getId(),
                                dto.getName());
        }

        private ProductStockResponse productStocksMapper(ProductStock dto) {
                List<StockVariationResponse> stockVariations = dto.getStockVariations().stream()
                                .map(this::stockVariationMapper).toList();

                return new ProductStockResponse(
                                dto.getId(),
                                dto.getStockQuantity(),
                                dto.getAdditionalPrice(),
                                dto.getIsLimited(),
                                dto.getReplenishQuantity(),
                                stockVariations);

        }

        public StockVariationResponse stockVariationMapper(StockVariation dto) {
                VariationOptionResponse variationOption = new VariationOptionResponse(
                                dto.getVariationOption().getId(),
                                dto.getVariationOption().getName());
                return new StockVariationResponse(
                                dto.getVariation().getId(),
                                dto.getVariation().getName(),
                                variationOption);

        }

}