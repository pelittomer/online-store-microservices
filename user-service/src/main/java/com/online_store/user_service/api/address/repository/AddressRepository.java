package com.online_store.user_service.api.address.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.online_store.user_service.api.address.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(Long user);

    @Query("SELECT a FROM Address a WHERE a.user = :userId AND a.id = :id")
    Optional<Address> findByUserAndId(@Param("userId") Long userId, @Param("id") Long id);
}
