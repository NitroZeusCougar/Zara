package com.zara.prices.infrastructure.rest.exception;

import com.zara.prices.domain.exception.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Centralized exception handler: maps domain exceptions to HTTP responses.
 * Uses RFC 7807 Problem Detail format (native in Spring 6+ / Spring Boot 3+).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link PriceNotFoundException}: returns 404 Not Found.
     *
     * @param ex the exception carrying the error message
     * @return RFC 7807 problem detail with 404 status and error description
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ProblemDetail handlePriceNotFound(PriceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setDetail(ex.getMessage());
        return problem;
    }
}
