package com.zara.prices.domain.rule;

import com.zara.prices.domain.exception.PriceNotFoundException;
import com.zara.prices.domain.model.Price;

import java.util.Comparator;
import java.util.List;

/**
 * Domain rule: selects the applicable price from a list of candidates.
 * When multiple prices overlap in time, the one with the highest priority wins.
 */
public class PriceSelectionRule {

    private PriceSelectionRule() {
        // Utility class — no instances
    }

    /**
     * Selects the price with the highest priority from the given candidates.
     *
     * @param candidates non-null list of prices valid for the requested date, product, and brand
     * @return the price with the highest {@code priority} value
     * @throws PriceNotFoundException if {@code candidates} is empty
     */
    public static Price select(List<Price> candidates) {
        return candidates.stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(PriceNotFoundException::new);
    }
}
