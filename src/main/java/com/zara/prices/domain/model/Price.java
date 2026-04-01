package com.zara.prices.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain entity representing a price tariff for a product and brand.
 * POJO — no framework dependencies.
 */
@Getter
@Builder
@EqualsAndHashCode
public class Price {

    /** Foreign key of the brand group (e.g. 1 = ZARA). */
    private Long brandId;

    /** Product identifier. */
    private Long productId;

    /**
     * Price tariff identifier.
     * Note: despite the name, this is a tariff ID, not a monetary amount.
     */
    private Long priceList;

    /** Start of the date range in which this price applies. */
    private LocalDateTime startDate;

    /** End of the date range in which this price applies. */
    private LocalDateTime endDate;

    /**
     * Disambiguation field: when two tariffs overlap, the one with the
     * highest priority value takes precedence.
     */
    private int priority;

    /** Final sale price. */
    private BigDecimal price;

    /** ISO currency code (e.g. "EUR"). */
    private String currency;
}
