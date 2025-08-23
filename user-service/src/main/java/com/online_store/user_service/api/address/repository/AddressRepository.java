package com.online_store.user_service.api.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.user_service.api.address.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
