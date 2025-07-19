package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Discount;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PagingDTO;
import com.guisebastiao.ecommerceapi.dto.request.DiscountRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.DiscountResponseDTO;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.mapper.DiscountMapper;
import com.guisebastiao.ecommerceapi.repository.DiscountRepository;
import com.guisebastiao.ecommerceapi.service.DiscountService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
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
    public DefaultDTO<Void> createDiscount(DiscountRequestDTO discountRequestDTO) {
        Discount discount = this.discountMapper.toEntity(discountRequestDTO);

        this.discountRepository.save(discount);

        return new DefaultDTO<Void>(Boolean.TRUE, "Desconto criado com sucesso", null);
    }

    @Override
    public DefaultDTO<PageResponseDTO<DiscountResponseDTO>> findAllDiscounts(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("name").ascending());

        Page<Discount> resultPage = this.discountRepository.findAll(pageable);

        PagingDTO pagingDTO = new PagingDTO(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<DiscountResponseDTO> dataResponse = resultPage.getContent().stream().map(this.discountMapper::toDto).toList();

        PageResponseDTO<DiscountResponseDTO> data = new PageResponseDTO<DiscountResponseDTO>(dataResponse, pagingDTO);

        return new DefaultDTO<PageResponseDTO<DiscountResponseDTO>>(Boolean.TRUE, "Descontos retornados com sucesso", data);
    }

    @Override
    public DefaultDTO<Void> updateDiscount(String discountId, DiscountRequestDTO discountRequestDTO) {
        Discount discount = this.findById(discountId);

        discount.setName(discountRequestDTO.name());
        discount.setPercent(discountRequestDTO.percent());
        discount.setStartDate(discountRequestDTO.startDate());
        discount.setEndDate(discountRequestDTO.endDate());
        discount.setActive(discountRequestDTO.active());

        this.discountRepository.save(discount);

        return new DefaultDTO<Void>(Boolean.TRUE, "Desconto atualizado com sucesso", null);
    }

    @Override
    public DefaultDTO<Void> deleteDiscount(String discountId) {
        Discount discount = this.findById(discountId);

        this.discountRepository.delete(discount);

        return new DefaultDTO<Void>(Boolean.TRUE, "Desconto excluido com sucesso", null);
    }

    private Discount findById(String discountId) {
        return this.discountRepository.findById(UUIDConverter.toUUID(discountId))
                .orElseThrow(() -> new EntityNotFoundException("Desconto não encontrado"));
    }
}
