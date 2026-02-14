package com.myspring.spring_crud.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.myspring.spring_crud.dto.CustomerRequest;
import com.myspring.spring_crud.dto.CustomerResponse;
import com.myspring.spring_crud.dto.CustomerUpdateRequest;
import com.myspring.spring_crud.dto.PagedResponse;
import com.myspring.spring_crud.exception.BadRequestException;
import com.myspring.spring_crud.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest req,
            UriComponentsBuilder uriBuilder) {
        CustomerResponse created = service.create(req);
        URI location = uriBuilder.path("/api/customers/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<CustomerResponse>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "newest") String sort) {

        if (page < 1) {
            throw new BadRequestException("page must be >= 1");
        }
        if (size < 1 || size > 100) {
            throw new BadRequestException("size must be between 1 and 100");
        }

        int clientPage = Math.max(page, 1);
        int pageIndex = clientPage - 1;

        Sort sortObj = "oldest".equalsIgnoreCase(sort)
                ? Sort.by("createdAt").ascending()
                : Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(pageIndex, size, sortObj);
        Page<CustomerResponse> results = service.list(pageable);

        PagedResponse<CustomerResponse> body = new PagedResponse<>(
                results.getContent(),
                clientPage,
                results.getSize(),
                results.getTotalElements(),
                results.getTotalPages());

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> patchCustomer(
            @PathVariable("id") UUID id,
            @RequestBody @Valid CustomerUpdateRequest updateReq) {

        CustomerResponse updated = service.update(id, updateReq);
        return ResponseEntity.accepted().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
