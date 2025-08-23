package com.online_store.auth_service.api.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online_store.auth_service.api.auth.dto.AuthRequest;
import com.online_store.auth_service.api.user.model.Role;
import com.online_store.auth_service.api.user.model.User;
import com.online_store.auth_service.api.user.service.UserService;
import com.online_store.auth_service.common.filter.utils.JwtUtilsService;
import com.online_store.auth_service.common.response.ApiResponse;
import com.online_store.auth_service.common.response.ErrorResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {
    @Value("${application.security.jwt.refresh-token.expiration}")
    private Integer refreshExpiration;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final JwtUtilsService jwtUtilsService;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;

    public AuthService(UserService userService,
            JwtUtilsService jwtUtilsService,
            AuthenticationManager authenticationManager,
            ObjectMapper objectMapper,
            UserDetailsService userDetailsService) {
        this.userService = userService;
        this.jwtUtilsService = jwtUtilsService;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.userDetailsService = userDetailsService;
    }

    public String register(AuthRequest dto,
            Role role) {
        logger.info("Starting user registration for email: {}", dto.email());

        Role actualRole = mapRoleParamToEnum(role);
        userService.createUser(dto, actualRole);
        logger.info("User registered successfully: {}", dto.email());

        return String.format("User %s registered successfully.", dto.email());
    }

    public String login(AuthRequest dto,
            Role role,
            HttpServletResponse response) {
        logger.info("User login attempt for email: {}", dto.email());

        authenticateUser(dto);
        User user = userService.findUserByEmail(dto.email());
        verifyUserRole(user, role);

        String accessToken = jwtUtilsService.generateToken(user);
        String refreshToken = jwtUtilsService.generateRefreshToken(user);

        addRefreshTokenCookie(refreshToken, response);

        logger.info("User {} logged in successfully.", dto.email());
        return accessToken;
    }

    public String logout(HttpServletResponse response) {
        logger.info("User logout initiated.");

        clearRefreshTokenCookie(response);

        logger.info("User logged out successfully.");
        return "You have successfully logged out.";
    }

    public void refreshToken(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        logger.info("Refreshing token process started.");

        String refreshToken = extractRefreshTokenFromCookie(request);
        if (refreshToken == null) {
            logger.warn("Refresh token not found in cookie during refresh attempt.");
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Refresh token not found.");
            return;
        }
        try {
            handleTokenRefresh(refreshToken, response);
            logger.info("Token refreshed successfully.");
        } catch (UsernameNotFoundException e) {
            logger.error("Username not found during token refresh: {}", e.getMessage());
            sendErrorResponse(response, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred during token refresh: {}", e.getMessage(), e);
            sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred: " + e.getMessage());
        }
    }

    private Role mapRoleParamToEnum(Role role) {
        logger.debug("Mapping role parameter to enum: {}", role);
        return role == Role.SELLER ? Role.SELLER : Role.CUSTOMER;
    }

    private void authenticateUser(AuthRequest dto) {
        logger.debug("Authenticating user: {}", dto.email());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
            logger.debug("User {} authenticated successfully.", dto.email());
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user: {}. Reason: Invalid credentials.", dto.email());
            throw new BadCredentialsException("Invalid email or password!");
        }
    }

    private void addRefreshTokenCookie(String refreshToken,
            HttpServletResponse response) {
        logger.debug("Adding refresh token cookie to response.");

        Cookie refreshTokenCookie = new Cookie("jwt", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(refreshExpiration);
        response.addCookie(refreshTokenCookie);

        logger.debug("Refresh token cookie added successfully.");
    }

    private void verifyUserRole(User user, Role role) {
        if (user.getRole() != role) {
            logger.warn("Login role mismatch. User {} is a {}, but attempted to log in as a {}.",
                    user.getEmail(), user.getRole(), role);
            throw new BadCredentialsException("Invalid credentials or role.");
        }
    }

    private void clearRefreshTokenCookie(HttpServletResponse response) {
        logger.debug("Clearing refresh token cookie.");

        Cookie refreshTokenCookie = new Cookie("jwt", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);

        logger.debug("Refresh token cookie cleared successfully.");
    }

    private void sendErrorResponse(HttpServletResponse response,
            HttpStatus status,
            String message) throws IOException {
        logger.info("Sending error response with status {}: {}", status.value(), message);
        response.setContentType("application/json");
        response.setStatus(status.value());
        objectMapper.writeValue(response.getOutputStream(), new ErrorResponse(message, status.value()));
    }

    private void sendSuccessResponse(HttpServletResponse response,
            String accessToken) throws IOException {
        logger.info("Sending success response.");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getOutputStream(), ApiResponse.success("Success", accessToken));
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        logger.debug("Extracting refresh token from cookies.");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    logger.debug("Refresh token found in cookie.");
                    return cookie.getValue();
                }
            }
        }
        logger.debug("No refresh token found in cookies.");
        return null;
    }

    private void handleTokenRefresh(String refreshToken,
            HttpServletResponse response) throws IOException {
        final String userEmail = jwtUtilsService.extractUsername(refreshToken);
        if (userEmail == null || userEmail.trim().isEmpty()) {
            logger.warn("Invalid token provided for refresh, user email is null or empty.");
            sendErrorResponse(response, HttpStatus.BAD_REQUEST, "Invalid token.");
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        if (!jwtUtilsService.isTokenValid(refreshToken, userDetails)) {
            logger.warn("Invalid refresh token provided for user: {}", userEmail);
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid refresh token.");
            return;
        }

        String accessToken = jwtUtilsService.generateToken(userDetails);
        sendSuccessResponse(response, accessToken);
    }
}
