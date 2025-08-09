package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Discount;
import com.guisebastiao.ecommerceapi.dto.DefaultResponse;
import com.guisebastiao.ecommerceapi.dto.PageResponse;
import com.guisebastiao.ecommerceapi.dto.Paging;
import com.guisebastiao.ecommerceapi.dto.request.discount.DiscountRequest;
import com.guisebastiao.ecommerceapi.dto.response.discount.DiscountResponse;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.mapper.DiscountMapper;
import com.guisebastiao.ecommerceapi.repository.DiscountRepository;
import com.guisebastiao.ecommerceapi.service.DiscountService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountMapper discountMapper;

    @Override
    public DefaultResponse<Void> createDiscount(DiscountRequest discountRequest) {
        Discount discount = this.discountMapper.toEntity(discountRequest);

        this.discountRepository.save(discount);

        return new DefaultResponse<Void>(true, "Desconto criado com sucesso", null);
    }

    @Override
    public DefaultResponse<PageResponse<DiscountResponse>> findAllDiscounts(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("name").ascending());

        Page<Discount> resultPage = this.discountRepository.findAll(pageable);

        Paging paging = new Paging(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<DiscountResponse> dataResponse = resultPage.getContent().stream().map(this.discountMapper::toDTO).toList();

        PageResponse<DiscountResponse> data = new PageResponse<DiscountResponse>(dataResponse, paging);

        return new DefaultResponse<PageResponse<DiscountResponse>>(true, "Descontos retornados com sucesso", data);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> updateDiscount(String discountId, DiscountRequest discountRequest) {
        Discount discount = this.findById(discountId);

        discount.setName(discountRequest.name());
        discount.setPercent(discountRequest.percent());
        discount.setEndDate(discountRequest.endDate());

        this.discountRepository.save(discount);

        return new DefaultResponse<Void>(true, "Desconto atualizado com sucesso", null);
    }

    @Override
    @Transactional
    public DefaultResponse<Void> deleteDiscount(String discountId) {
        Discount discount = this.findById(discountId);

        this.discountRepository.delete(discount);

        return new DefaultResponse<Void>(true, "Desconto excluido com sucesso", null);
    }

    private Discount findById(String discountId) {
        return this.discountRepository.findById(UUIDConverter.toUUID(discountId))
                .orElseThrow(() -> new EntityNotFoundException("Desconto não encontrado"));
    }
}
