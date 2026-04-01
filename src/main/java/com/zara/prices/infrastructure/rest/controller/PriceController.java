package com.zara.prices.infrastructure.rest.controller;

import com.zara.prices.application.inbound.FindApplicablePriceUseCase;
import com.zara.prices.infrastructure.rest.dto.PriceResponse;
import com.zara.prices.infrastructure.rest.mapper.PriceRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * REST adapter: entry point for the price query API.
 * Delegates business logic to {@link FindApplicablePriceUseCase}.
 * Never exposes the domain model directly — uses {@link PriceResponse} DTO.
 */
@RestController
@RequestMapping("/api/v1/prices")
@Tag(name = "Prices", description = "Price query operations")
public class PriceController {

    private final FindApplicablePriceUseCase useCase;
    private final PriceRestMapper mapper;

    /**
     * @param useCase inbound port for the price query use case
     * @param mapper  mapper to convert domain model to REST response DTO
     */
    public PriceController(FindApplicablePriceUseCase useCase, PriceRestMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    /**
     * Returns the applicable price for a product, brand, and application date.
     * When multiple tariffs overlap, the one with the highest priority is returned.
     *
     * @param date      application date in ISO-8601 format (yyyy-MM-dd'T'HH:mm:ss)
     * @param productId product identifier
     * @param brandId   brand identifier
     * @return the applicable {@link PriceResponse}
     */
    @GetMapping
    @Operation(
            summary = "Get applicable price",
            description = "Returns the price tariff with the highest priority applicable for the given date"
    )
    @ApiResponse(responseCode = "200", description = "Applicable price found")
    @ApiResponse(responseCode = "404", description = "No applicable price found")
    @ApiResponse(responseCode = "400", description = "Missing or invalid request parameters")
    public PriceResponse getApplicablePrice(
            @Parameter(description = "Application date (yyyy-MM-dd'T'HH:mm:ss)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,

            @Parameter(description = "Product identifier", required = true)
            @RequestParam Long productId,

            @Parameter(description = "Brand identifier", required = true)
            @RequestParam Long brandId
    ) {
        return mapper.toResponse(useCase.find(productId, brandId, date));
    }
}
