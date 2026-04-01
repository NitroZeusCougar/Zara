package com.zara.prices.infrastructure.db.mapper;

import com.zara.prices.domain.model.Price;
import com.zara.prices.infrastructure.db.entity.PriceJpaEntity;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper: converts between {@link PriceJpaEntity} and {@link Price}.
 * Implementation is generated at compile time — no reflection at runtime.
 */
@Mapper(componentModel = "spring")
public interface PriceJpaMapper {

    /**
     * Converts a JPA entity to the domain model.
     * All field names match between source and target, so no explicit mappings are needed.
     *
     * @param entity the JPA entity
     * @return the domain {@link Price}
     */
    Price toDomain(PriceJpaEntity entity);
}
