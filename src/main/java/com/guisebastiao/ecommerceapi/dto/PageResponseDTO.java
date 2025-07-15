package com.guisebastiao.ecommerceapi.dto;

import java.util.List;

public record PageResponseDTO<T>(
        List<T> items,
        PagingDTO paging
) { }
