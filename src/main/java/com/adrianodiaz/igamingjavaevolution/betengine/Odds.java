package com.adrianodiaz.igamingjavaevolution.betengine;

import java.math.BigDecimal;

/**
 * Records were included in Java 16. An immutable class to transport data: you only declare the components and the
 * compiler generates the constructor with all fields, an accessor for every field (called value(), not getValue(),
 * without the get prefix), equals(), hashCode() and toString().
 *
 * @param value Uses BigDecimal (not double) for money: exact base-10 arithmetics, avoid floating-point rounding errors.
 */
public record Odds(BigDecimal value) {
    public Odds {
        if (value == null) {
            throw new IllegalArgumentException("Odds cannot be null");
        }
        if (value.compareTo(BigDecimal.ONE) <= 0) {
            throw new IllegalArgumentException("Odds must be greater than 1.0, got: " + value);
        }
    }
}

