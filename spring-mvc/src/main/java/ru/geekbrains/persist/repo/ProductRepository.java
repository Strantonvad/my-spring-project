package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByTitle(String title);

    List<Product> findByTitleLike(String loginPattern);

    List<Product> findByCostGreaterThan(BigDecimal minPrice);

    List<Product> findByCostLessThanEqual(BigDecimal maxPrice);

    List<Product> findByCostBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
