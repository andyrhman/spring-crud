package com.myspring.spring_crud.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myspring.spring_crud.dto.CustomerRequest;
import com.myspring.spring_crud.dto.CustomerResponse;
import com.myspring.spring_crud.dto.CustomerUpdateRequest;

public interface CustomerInterface {
    CustomerResponse create(CustomerRequest req);

    CustomerResponse getById(UUID id);

    Page<CustomerResponse> list(Pageable pageable);

    CustomerResponse update(UUID id, CustomerUpdateRequest req);

    // CustomerResponse partialUpdate(UUID id, CustomerUpdateRequest req);

    void delete(UUID id);
}
