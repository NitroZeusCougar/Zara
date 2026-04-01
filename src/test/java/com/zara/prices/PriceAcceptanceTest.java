package com.zara.prices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Acceptance tests validating the 5 business scenarios defined in the requirements.
 * Uses the full Spring context with a real H2 database loaded from data.sql.
 * No mocks — end-to-end validation of the entire request pipeline.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PriceAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String URL = "/api/v1/prices";
    private static final String PRODUCT_ID = "35455";
    private static final String BRAND_ID = "1";

    /**
     * Test 1: request at 10:00 on June 14.
     * Only tariff 1 is active → price 35.50.
     */
    @Test
    void shouldReturnTariff1Price_whenRequestedAt10OnJune14() throws Exception {
        mockMvc.perform(get(URL)
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    /**
     * Test 2: request at 16:00 on June 14.
     * Tariffs 1 and 2 overlap; tariff 2 has higher priority → price 25.45.
     */
    @Test
    void shouldReturnTariff2Price_whenRequestedAt16OnJune14() throws Exception {
        mockMvc.perform(get(URL)
                        .param("date", "2020-06-14T16:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    /**
     * Test 3: request at 21:00 on June 14.
     * Tariff 2 has ended (18:30); only tariff 1 is active → price 35.50.
     */
    @Test
    void shouldReturnTariff1Price_whenRequestedAt21OnJune14() throws Exception {
        mockMvc.perform(get(URL)
                        .param("date", "2020-06-14T21:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    /**
     * Test 4: request at 10:00 on June 15.
     * Tariffs 1 and 3 overlap; tariff 3 has higher priority → price 30.50.
     */
    @Test
    void shouldReturnTariff3Price_whenRequestedAt10OnJune15() throws Exception {
        mockMvc.perform(get(URL)
                        .param("date", "2020-06-15T10:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    /**
     * Test 5: request at 21:00 on June 16.
     * Only tariff 4 is active (from 16:00 on June 15) → price 38.95.
     */
    @Test
    void shouldReturnTariff4Price_whenRequestedAt21OnJune16() throws Exception {
        mockMvc.perform(get(URL)
                        .param("date", "2020-06-16T21:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }
}
