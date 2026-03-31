package com.zara.prices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Zara Prices Service application.
 * Exposes a REST endpoint to query applicable prices for a product and brand
 * on a given date, applying the highest-priority tariff when multiple rates overlap.
 */
@SpringBootApplication
public class ZaraPricesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZaraPricesApplication.class, args);
    }
}
