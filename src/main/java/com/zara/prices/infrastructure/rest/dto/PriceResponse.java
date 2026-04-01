package com.zara.prices.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * REST response DTO for the applicable price query.
 * Immutable Java record — no setters, thread-safe by design.
 *
 * @param productId  product identifier
 * @param brandId    brand identifier (e.g. 1 = ZARA)
 * @param priceList  price tariff identifier
 * @param startDate  start of the tariff's validity period
 * @param endDate    end of the tariff's validity period
 * @param price      final sale price
 */
@Schema(description = "Applicable price for the requested product, brand, and date")
public record PriceResponse(

        @Schema(description = "Product identifier", example = "35455")
        Long productId,

        @Schema(description = "Brand identifier", example = "1")
        Long brandId,

        @Schema(description = "Price tariff identifier", example = "1")
        Long priceList,

        @Schema(description = "Start of the tariff validity period")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startDate,

        @Schema(description = "End of the tariff validity period")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime endDate,

        @Schema(description = "Final sale price", example = "35.50")
        BigDecimal price
) {}
