package com.zara.prices.domain.outbound.repository;

import com.zara.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Outbound port: defines how the domain queries prices.
 * Implemented by the JPA adapter in the infrastructure layer.
 */
public interface PriceRepository {

    /**
     * Returns all prices applicable for the given product, brand, and date.
     * A price is applicable when {@code date} falls within [startDate, endDate].
     *
     * @param productId the product identifier
     * @param brandId   the brand identifier
     * @param date      the application date
     * @return list of applicable prices (may be empty)
     */
    List<Price> findApplicable(Long productId, Long brandId, LocalDateTime date);
}
