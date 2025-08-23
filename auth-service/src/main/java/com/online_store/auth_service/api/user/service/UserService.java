package com.online_store.auth_service.api.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import com.online_store.auth_service.api.auth.dto.AuthRequest;
import com.online_store.auth_service.api.user.dto.UserResponse;
import com.online_store.auth_service.api.user.exception.UserAlreadyExistsException;
import com.online_store.auth_service.api.user.exception.UserNotFoundException;
import com.online_store.auth_service.api.user.model.Role;
import com.online_store.auth_service.api.user.model.User;
import com.online_store.auth_service.api.user.repository.UserRepository;
import com.online_store.auth_service.common.filter.utils.UtilsService;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final UtilsService utilsService;

    public UserService(UserRepository repository,
            UtilsService utilsService) {
        this.repository = repository;
        this.utilsService = utilsService;
    }

    public UserResponse getCurrentUser() {
        logger.info("Attempting to retrieve account information for the authenticated user.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            logger.warn("Authentication object is null. User is not authenticated.");
            throw new IllegalStateException("User not authenticated.");
        }

        String userEmail = authentication.getName();
        logger.debug("Found authenticated user with email: {}", userEmail);

        try {
            User user = findUserByEmail(userEmail);
            logger.info("Account information successfully retrieved for user: {}", userEmail);
            return new UserResponse(user.getId(),
                    user.getEmail(),
                    user.getRole());
        } catch (UserNotFoundException e) {
            logger.error("User with email {} not found while trying to get account details.", userEmail);
            throw e;
        }
    }

    public void createUser(AuthRequest dto, Role role) {
        handleExistingUser(dto.email());
        User user = createUserMappper(dto, role);
        repository.save(user);
    }

    private User createUserMappper(AuthRequest dto,
            Role role) {
        logger.debug("Creating user object for email: {}", dto.email());
        String hashedPassword = utilsService.hashedPassword(dto.password());
        return new User(dto.email(), hashedPassword, role);
    }

    private void handleExistingUser(String email) {
        logger.debug("Checking for existing user with email: {}", email);
        if (repository.findByEmail(email).isPresent()) {
            logger.warn("Registration attempt for existing email: {}", email);
            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
        }
        logger.debug("User with email {} does not exist. Proceeding with registration.", email);
    }

    public User findUserByEmail(String email) {
        logger.debug("Searching for user with email: {}", email);
        return repository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new UserNotFoundException("User not found with email:" + email);
                });
    }

}
