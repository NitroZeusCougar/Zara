package com.zara.prices.infrastructure.rest.mapper;

import com.zara.prices.domain.model.Price;
import com.zara.prices.infrastructure.rest.dto.PriceResponse;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper: converts {@link Price} domain model to {@link PriceResponse} DTO.
 * Implementation is generated at compile time — no reflection at runtime.
 */
@Mapper(componentModel = "spring")
public interface PriceRestMapper {

    /**
     * Converts a domain price to the REST response DTO.
     * All field names match between source and target, so no explicit mappings are needed.
     * The {@code currency} field present in {@link Price} is intentionally omitted
     * from {@link PriceResponse} as it is not required by the API contract.
     *
     * @param price the domain model
     * @return the REST response DTO
     */
    PriceResponse toResponse(Price price);
}
