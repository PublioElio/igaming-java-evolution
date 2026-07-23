package com.adrianodiaz.igamingjavaevolution.betengine;

import java.math.BigDecimal;

/**
 * Record (Java 16): immutable data carrier (check Odds class for more details)
 */
public record Stake(BigDecimal amount) {
    public Stake {
        if (amount == null) {
            throw new IllegalArgumentException("Stake amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Stake amount must be positive, got: " + amount);
        }
    }
}
