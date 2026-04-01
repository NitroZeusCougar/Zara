package com.zara.prices.application.service;

import com.zara.prices.domain.exception.PriceNotFoundException;
import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.outbound.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link FindApplicablePriceService}.
 * PriceRepository is mocked — no Spring context loaded.
 */
@ExtendWith(MockitoExtension.class)
class FindApplicablePriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private FindApplicablePriceService service;

    private static final LocalDateTime DATE = LocalDateTime.of(2020, 6, 14, 10, 0);
    private static final long PRODUCT_ID = 35455L;
    private static final long BRAND_ID = 1L;

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Price buildPrice(int priority, double amount) {
        return Price.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(1L)
                .startDate(DATE.minusHours(1))
                .endDate(DATE.plusHours(1))
                .priority(priority)
                .price(BigDecimal.valueOf(amount))
                .currency("EUR")
                .build();
    }

    // ── Tests ─────────────────────────────────────────────────────────────────

    @Test
    void shouldReturnApplicablePrice_whenSingleCandidateExists() {
        // Arrange
        Price expected = buildPrice(0, 35.50);
        when(priceRepository.findApplicable(PRODUCT_ID, BRAND_ID, DATE))
                .thenReturn(List.of(expected));

        // Act
        Price result = service.find(PRODUCT_ID, BRAND_ID, DATE);

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldReturnHighestPriorityPrice_whenMultipleCandidatesExist() {
        // Arrange
        Price lowPriority  = buildPrice(0, 35.50);
        Price highPriority = buildPrice(1, 25.45);
        when(priceRepository.findApplicable(PRODUCT_ID, BRAND_ID, DATE))
                .thenReturn(List.of(lowPriority, highPriority));

        // Act
        Price result = service.find(PRODUCT_ID, BRAND_ID, DATE);

        // Assert
        assertThat(result).isEqualTo(highPriority);
    }

    @Test
    void shouldThrowPriceNotFoundException_whenNoCandidatesExist() {
        // Arrange
        when(priceRepository.findApplicable(PRODUCT_ID, BRAND_ID, DATE))
                .thenReturn(Collections.emptyList());

        // Act + Assert
        assertThatThrownBy(() -> service.find(PRODUCT_ID, BRAND_ID, DATE))
                .isInstanceOf(PriceNotFoundException.class);
    }
}
