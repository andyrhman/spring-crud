package com.myspring.spring_crud.dto;

import com.myspring.spring_crud.entity.Customer;

public class CustomerMapper {
    public static CustomerResponse toResponse(Customer c) {
        return CustomerResponse.builder()
            .id(c.getId())
            .fullName(c.getFullName())
            .email(c.getEmail())
            .phone(c.getPhone())
            .createdAt(c.getCreatedAt())
            .build();
    }

    public static Customer toEntity(CustomerRequest r) {
        return Customer.builder()
            .fullName(r.getFullName())
            .email(r.getEmail())
            .phone(r.getPhone())
            .build();
    }
}
