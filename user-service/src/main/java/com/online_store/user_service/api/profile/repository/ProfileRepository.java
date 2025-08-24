package com.online_store.user_service.api.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.user_service.api.profile.model.Profile;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsByUser(Long user);

    Optional<Profile> findByUser(Long user);
}
