package com.zara.prices.domain.exception;

/**
 * Thrown when no applicable price is found for the given product, brand, and date.
 */
public class PriceNotFoundException extends RuntimeException {

    /**
     * Constructor used when full query context is available (e.g. in the service layer).
     *
     * @param productId the product identifier that was queried
     * @param brandId   the brand identifier that was queried
     */
    public PriceNotFoundException(Long productId, Long brandId) {
        super(String.format(
                "No applicable price found for productId=%d and brandId=%d", productId, brandId));
    }

    /**
     * Constructor used when only an empty candidate list is known,
     * without access to the original query parameters (e.g. in domain rules).
     */
    public PriceNotFoundException() {
        super("No applicable price found: candidate list is empty");
    }
}
