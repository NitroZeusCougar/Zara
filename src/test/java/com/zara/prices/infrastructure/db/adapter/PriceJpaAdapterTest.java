package com.zara.prices.infrastructure.db.adapter;

import com.zara.prices.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link PriceJpaAdapter}.
 * Uses a real H2 database loaded with schema.sql + data.sql.
 * No mocks — validates the full JPA query and mapping pipeline.
 */
@SpringBootTest
@Transactional
class PriceJpaAdapterTest {

    @Autowired
    private PriceJpaAdapter adapter;

    @Test
    void shouldReturnOneCandidate_whenDateMatchesOnlyOneTariff() {
        // Arrange — 10:00 on June 14: only tariff 1 is active (00:00 – 23:59:59)
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);

        // Act
        List<Price> result = adapter.findApplicable(35455L, 1L, date);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPriceList()).isEqualTo(1L);
    }

    @Test
    void shouldReturnTwoCandidates_whenDateOverlapsTwoTariffs() {
        // Arrange — 16:00 on June 14: tariff 1 (00:00–23:59:59) and tariff 2 (15:00–18:30) overlap
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);

        // Act
        List<Price> result = adapter.findApplicable(35455L, 1L, date);

        // Assert
        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnEmptyList_whenNoTariffMatchesDate() {
        // Arrange — date well outside all tariff ranges
        LocalDateTime date = LocalDateTime.of(2019, 1, 1, 0, 0);

        // Act
        List<Price> result = adapter.findApplicable(35455L, 1L, date);

        // Assert
        assertThat(result).isEmpty();
    }
}
