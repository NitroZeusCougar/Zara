package com.zara.prices.application.service;

import com.zara.prices.application.inbound.FindApplicablePriceUseCase;
import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.outbound.repository.PriceRepository;
import com.zara.prices.domain.rule.PriceSelectionRule;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Orchestrates the "find applicable price" use case.
 * Delegates persistence to {@link PriceRepository} and selection logic
 * to {@link PriceSelectionRule}.
 */
@Service
public class FindApplicablePriceService implements FindApplicablePriceUseCase {

    private final PriceRepository priceRepository;

    /**
     * @param priceRepository outbound port for price persistence
     */
    public FindApplicablePriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Price find(Long productId, Long brandId, LocalDateTime date) {
        List<Price> candidates = priceRepository.findApplicable(productId, brandId, date);
        return PriceSelectionRule.select(candidates);
    }
}
