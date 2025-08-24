package com.online_store.user_service.api.profile.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.online_store.user_service.api.profile.dto.ProfileRequest;
import com.online_store.user_service.api.profile.dto.ProfileResponse;
import com.online_store.user_service.api.profile.model.Profile;
import com.online_store.user_service.api.profile.repository.ProfileRepository;
import com.online_store.user_service.common.utils.UtilsService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository repository;
    private final UtilsService utilsService;

    public ProfileService(ProfileRepository repository,
            UtilsService utilsService) {
        this.repository = repository;
        this.utilsService = utilsService;
    }

    // kafka integration
    public void createProfile(
            Long userId) {
        logger.info("Starting profile creation for user with DTO: {}", userId);
        logger.debug("Retrieved user ID: {}", userId);

        if (repository.existsByUser(userId)) {
            logger.warn("Attempted to create a profile for an existing user. User ID: {}", userId);
            throw new Error("Profile exists!");
        }

        Profile newProfile = createProfileMapper(userId);
        repository.save(newProfile);
        logger.info("Profile created and saved successfully for user ID: {}", userId);
    }

    public String updateProfile(ProfileRequest dto) {
        Long userId = utilsService.getCurrentUserId();
        logger.info("Attempting to update profile for user ID: {}", userId);

        Profile profile = findProfileByUserId(userId);
        mapAndUpdateProfile(profile, dto);
        repository.save(profile);

        logger.info("Profile updated successfully for user ID: {}", userId);
        return "Profile updated successfully.";
    }

    public ProfileResponse getProfile() {
        Long userId = utilsService.getCurrentUserId();
        logger.info("Attempting to retrieve profile for user ID: {}", userId);

        Profile profile = findProfileByUserId(userId);
        
        logger.info("Successfully retrieved and mapped profile for user ID: {}", userId);
        return getProfileMapper(profile);
    }

    private void mapAndUpdateProfile(Profile profile, ProfileRequest dto) {
        Optional.ofNullable(dto.firstName()).ifPresent(profile::setFirstName);
        Optional.ofNullable(dto.lastName()).ifPresent(profile::setLastName);
        Optional.ofNullable(dto.gender()).ifPresent(profile::setGender);
        Optional.ofNullable(dto.birthOfDate()).ifPresent(profile::setBirthOfDate);
    }

    private Profile findProfileByUserId(Long userId) {
        return repository.findByUser(userId)
                .orElseThrow(() -> {
                    logger.error("Profile not found for user ID: {}", userId);
                    return new EntityNotFoundException("Profile not found for user: " + userId);
                });
    }

    private Profile createProfileMapper(Long userId) {
        logger.debug("Mapping DTO to Profile object for user ID: {}", userId);
        return new Profile(userId);
    }

    private ProfileResponse getProfileMapper(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getBirthOfDate(),
                profile.getGender(),
                profile.getUser(),
                profile.getUpdatedAt());
    }
}
