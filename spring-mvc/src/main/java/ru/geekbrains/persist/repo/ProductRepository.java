package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product > {
    List<Product> findByTitle(String title);

    List<Product> findByTitleLike(String loginPattern);

    List<Product> findByCostGreaterThan(BigDecimal minPrice);

    List<Product> findByCostLessThanEqual(BigDecimal maxPrice);

    List<Product> findByCostBetween(BigDecimal minPrice, BigDecimal maxPrice);

//    @Query("from Product p where (p.title = :title or :title is null) " +
//        "and (p.cost >= :minPrice or :minPrice is null) and (p.cost <= :maxPrice or :maxPrice is null)")
//    List<Product> findByTitleAndCostBetween(@Param("title") String title,
//                                            @Param("minPrice") BigDecimal minPrice,
//                                            @Param("maxPrice") BigDecimal maxPrice);
}
