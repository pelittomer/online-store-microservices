package com.online_store.auth_service.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.auth_service.api.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
