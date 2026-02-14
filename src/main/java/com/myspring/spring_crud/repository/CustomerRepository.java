package com.myspring.spring_crud.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myspring.spring_crud.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>{
    Optional<Customer> findByEmail(String email);
    
}
