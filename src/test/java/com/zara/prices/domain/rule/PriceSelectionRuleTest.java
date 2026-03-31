package com.zara.prices.domain.rule;

import com.zara.prices.domain.exception.PriceNotFoundException;
import com.zara.prices.domain.model.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link PriceSelectionRule}.
 * No Spring context — pure domain logic.
 */
class PriceSelectionRuleTest {

    private static final LocalDateTime ANY_DATE = LocalDateTime.of(2020, 6, 14, 10, 0);

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Price buildPrice(int priority, double amount) {
        return Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1L)
                .startDate(ANY_DATE.minusHours(1))
                .endDate(ANY_DATE.plusHours(1))
                .priority(priority)
                .price(BigDecimal.valueOf(amount))
                .currency("EUR")
                .build();
    }

    // ── Tests ─────────────────────────────────────────────────────────────────

    @Test
    void shouldReturnTheOnlyCandidate_whenListHasOnePrice() {
        // Arrange
        Price expected = buildPrice(0, 35.50);

        // Act
        Price result = PriceSelectionRule.select(List.of(expected));

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldReturnHighestPriorityPrice_whenMultipleCandidatesExist() {
        // Arrange
        Price lowPriority  = buildPrice(0, 35.50);
        Price highPriority = buildPrice(1, 25.45);

        // Act
        Price result = PriceSelectionRule.select(List.of(lowPriority, highPriority));

        // Assert
        assertThat(result).isEqualTo(highPriority);
    }

    @Test
    void shouldReturnHighestPriorityPrice_whenCandidatesAreInReverseOrder() {
        // Arrange — higher priority first in the list; rule must not depend on order
        Price highPriority = buildPrice(1, 25.45);
        Price lowPriority  = buildPrice(0, 35.50);

        // Act
        Price result = PriceSelectionRule.select(List.of(highPriority, lowPriority));

        // Assert
        assertThat(result).isEqualTo(highPriority);
    }

    @Test
    void shouldThrowPriceNotFoundException_whenCandidateListIsEmpty() {
        assertThatThrownBy(() -> PriceSelectionRule.select(Collections.emptyList()))
                .isInstanceOf(PriceNotFoundException.class);
    }
}
