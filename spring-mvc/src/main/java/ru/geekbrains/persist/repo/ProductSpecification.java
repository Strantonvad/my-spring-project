package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;

public final class ProductSpecification {

    public static Specification<Product> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Product> titleLike(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Product> greaterThanMinPrice(BigDecimal minPrice) {
        return (root, query, builder) -> builder.greaterThan(root.get("cost"), minPrice);
    }

    public static Specification<Product> lessThanMaxPrice(BigDecimal maxPrice) {
        return (root, query, builder) -> builder.lessThan(root.get("cost"), maxPrice);
    }
}
