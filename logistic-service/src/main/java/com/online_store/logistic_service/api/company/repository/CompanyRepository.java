package com.online_store.logistic_service.api.company.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.online_store.logistic_service.api.company.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c WHERE c.user = :userId OR c.name = :name OR c.taxId = :taxId")
    Optional<Company> findByUserIdOrNameOrTaxId(
            @Param("userId") Long userId,
            @Param("name") String name,
            @Param("taxId") String taxId);

    Optional<Company> findByUser(Long user);
}
