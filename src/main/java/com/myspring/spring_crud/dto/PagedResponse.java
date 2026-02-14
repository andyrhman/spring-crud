package com.myspring.spring_crud.dto;

public record PagedResponse<T>(
    java.util.List<T> content,
    int page,
    int size,
    long total,
    int last_page
) {}
