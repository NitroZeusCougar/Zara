package com.zara.prices.infrastructure.db.repository;

import com.zara.prices.infrastructure.db.entity.PriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for {@link PriceJpaEntity}.
 * Provides the query to retrieve all prices applicable for a given product, brand, and date.
 */
public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Long> {

    /**
     * Returns all prices where the given date falls within [startDate, endDate]
     * and the product and brand match.
     * The selection of the highest-priority candidate is delegated to the domain rule.
     *
     * @param productId the product identifier
     * @param brandId   the brand identifier
     * @param date      the application date
     * @return list of applicable price entities (may be empty)
     */
    @Query("""
            SELECT p FROM PriceJpaEntity p
            WHERE p.productId = :productId
              AND p.brandId   = :brandId
              AND p.startDate <= :date
              AND p.endDate   >= :date
            """)
    List<PriceJpaEntity> findApplicable(
            @Param("productId") Long productId,
            @Param("brandId")   Long brandId,
            @Param("date")      LocalDateTime date
    );
}
