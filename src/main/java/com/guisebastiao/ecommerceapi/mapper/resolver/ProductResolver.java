package com.guisebastiao.ecommerceapi.mapper.resolver;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.domain.Review;
import com.guisebastiao.ecommerceapi.security.ClientAuthProvider;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ProductResolver {

    @Autowired
    private ClientAuthProvider clientAuthProvider;

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

    @Named("resolveReviewRating")
    public Double resolveReviewRating(Product product) {
        return product.getReviews()
                .stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    @Named("resolveAlreadyReviewed")
    public Boolean resolveAlreadyReviewed(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) return false;

        Client client = this.clientAuthProvider.getClientAuthenticated();

        return product.getReviews()
                .stream()
                .anyMatch(e -> e.getClient().getId().equals(client.getId()));
    }

    @Named("resolveIsFavorite")
    public Boolean resolveIsFavorite(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) return false;

        Client client = this.clientAuthProvider.getClientAuthenticated();

        return product.getClientFavorites()
                .stream()
                .anyMatch(e -> e.getClient().getId().equals(client.getId()));
    }
}
