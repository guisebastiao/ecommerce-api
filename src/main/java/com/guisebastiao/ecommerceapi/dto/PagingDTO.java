package com.guisebastiao.ecommerceapi.dto;

public record PagingDTO(
        long totalItems,
        long totalPages,
        long currentPage,
        long itemsPerPage
) { }
