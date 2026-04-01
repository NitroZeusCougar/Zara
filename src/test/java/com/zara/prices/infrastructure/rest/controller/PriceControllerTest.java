package com.zara.prices.infrastructure.rest.controller;

import com.zara.prices.application.inbound.FindApplicablePriceUseCase;
import com.zara.prices.domain.exception.PriceNotFoundException;
import com.zara.prices.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller tests for {@link PriceController}.
 * Uses @SpringBootTest + @AutoConfigureMockMvc (replacing @WebMvcTest removed in Spring Boot 4.x).
 * FindApplicablePriceUseCase is mocked to isolate HTTP contract verification.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FindApplicablePriceUseCase useCase;

    private static final String URL = "/api/v1/prices";

    @Test
    void shouldReturn200WithPriceResponse_whenPriceIsFound() throws Exception {
        // Arrange
        Price price = Price.builder()
                .productId(35455L)
                .brandId(1L)
                .priceList(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .currency("EUR")
                .build();
        when(useCase.find(eq(35455L), eq(1L), any(LocalDateTime.class))).thenReturn(price);

        // Act + Assert
        mockMvc.perform(get(URL)
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void shouldReturn404_whenPriceNotFound() throws Exception {
        // Arrange
        when(useCase.find(any(), any(), any())).thenThrow(new PriceNotFoundException(35455L, 1L));

        // Act + Assert
        mockMvc.perform(get(URL)
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400_whenRequiredParamIsMissing() throws Exception {
        // Act + Assert — missing 'date' param
        mockMvc.perform(get(URL)
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }
}
