package com.guisebastiao.ecommerceapi.dto;

public record Paging(
        long totalItems,
        long totalPages,
        long currentPage,
        long itemsPerPage
) { }
