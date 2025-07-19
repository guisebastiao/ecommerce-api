package com.guisebastiao.ecommerceapi.mapper.resolver;

import com.guisebastiao.ecommerceapi.domain.Product;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ProductResolver {

    @Named("resolveDiscount")
    public BigDecimal resolveDiscount(Product product) {
        if(product.getDiscount() == null) return null;

        BigDecimal price = product.getPrice();
        Double percent = product.getDiscount().getPercent();

        if (percent == null || price == null) {
            return price;
        }

        BigDecimal percentDecimal = BigDecimal.valueOf(percent).divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = price.multiply(percentDecimal);
        return price.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }

    @Named("resolveHaveDiscount")
    public Boolean resolveHaveDiscount(Product product) {
        return product.getDiscount() != null;
    }
}
