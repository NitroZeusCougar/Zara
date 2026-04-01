package com.zara.prices.application.inbound;

import com.zara.prices.domain.exception.PriceNotFoundException;
import com.zara.prices.domain.model.Price;

import java.time.LocalDateTime;

/**
 * Inbound port: entry point for the "find applicable price" use case.
 * Implemented by {@link com.zara.prices.application.service.FindApplicablePriceService}.
 */
public interface FindApplicablePriceUseCase {

    /**
     * Returns the applicable price for the given product, brand, and date.
     * When multiple prices overlap, the one with the highest priority is returned.
     *
     * @param productId the product identifier
     * @param brandId   the brand identifier
     * @param date      the application date
     * @return the applicable {@link Price}
     * @throws PriceNotFoundException if no price is found for the given parameters
     */
    Price find(Long productId, Long brandId, LocalDateTime date);
}
