package com.zara.prices.infrastructure.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA entity mapping the PRICES table.
 * Infrastructure concern only — not part of the domain model.
 */
@Entity
@Table(name = "PRICES")
@Getter
@Setter
@NoArgsConstructor
public class PriceJpaEntity {

    /** Auto-generated primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Foreign key of the brand group (e.g. 1 = ZARA). */
    @Column(name = "brand_id", nullable = false)
    private Long brandId;

    /** Start of the date range in which this price applies. */
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    /** End of the date range in which this price applies. */
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    /** Price tariff identifier. */
    @Column(name = "price_list", nullable = false)
    private Long priceList;

    /** Product identifier. */
    @Column(name = "product_id", nullable = false)
    private Long productId;

    /** Priority used to disambiguate overlapping tariffs. */
    @Column(name = "priority", nullable = false)
    private int priority;

    /** Final sale price. */
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** ISO currency code (e.g. "EUR"). */
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
}
