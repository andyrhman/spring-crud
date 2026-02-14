package com.myspring.spring_crud.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myspring.spring_crud.dto.CustomerMapper;
import com.myspring.spring_crud.dto.CustomerRequest;
import com.myspring.spring_crud.dto.CustomerResponse;
import com.myspring.spring_crud.dto.CustomerUpdateRequest;
import com.myspring.spring_crud.interfaces.CustomerInterface;
import com.myspring.spring_crud.repository.CustomerRepository;
import com.myspring.spring_crud.entity.Customer;
import com.myspring.spring_crud.exception.BadRequestException;
import com.myspring.spring_crud.exception.ConflictException;
import com.myspring.spring_crud.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerInterface {
    private final CustomerRepository repo;

    @Override
    @Transactional
    public CustomerResponse create(CustomerRequest req) {
        if (repo.findByEmail(req.getEmail()).isPresent()) {
            throw new ConflictException("Email already in use");
        }
        Customer entity = CustomerMapper.toEntity(req);
        Customer saved = repo.save(entity);
        return CustomerMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getById(UUID id) {
        Customer c = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return CustomerMapper.toResponse(c);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(CustomerMapper::toResponse);
    }

    @Override
    @Transactional
    public CustomerResponse update(UUID id, CustomerUpdateRequest req) {
        Customer c = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        // fullName -> if provided, must not be blank
        if (req.getFullName() != null) {
            if (req.getFullName().isBlank()) {
                throw new BadRequestException("fullName must not be blank");
            }
            c.setFullName(req.getFullName());
        }

        // email -> if provided, must not be blank and must be unique
        if (req.getEmail() != null) {
            if (req.getEmail().isBlank()) {
                throw new BadRequestException("email must not be blank");
            }
            // check uniqueness only if email actually changes
            if (!req.getEmail().equalsIgnoreCase(c.getEmail())) {
                repo.findByEmail(req.getEmail()).ifPresent(existing -> {
                    throw new ConflictException("Email already in use");
                });
                c.setEmail(req.getEmail());
            }
        }

        // phone -> if provided (blank allowed? we treat blank as valid to set empty
        // string)
        if (req.getPhone() != null) {
            // optional: reject blank phone -> uncomment next lines
            // if (req.getPhone().isBlank()) throw new BadRequestException("phone must not
            // be blank");
            c.setPhone(req.getPhone());
        }

        Customer saved = repo.save(c);
        return CustomerMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id))
            throw new NotFoundException("Customer not found");
        repo.deleteById(id);
    }
}
