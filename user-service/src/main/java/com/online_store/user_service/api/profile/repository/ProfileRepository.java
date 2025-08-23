package com.online_store.user_service.api.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.user_service.api.profile.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
