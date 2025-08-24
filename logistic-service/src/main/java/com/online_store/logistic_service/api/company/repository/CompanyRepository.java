package com.online_store.logistic_service.api.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.logistic_service.api.company.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
