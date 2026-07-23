package com.adrianodiaz.igamingjavaevolution.betengine;

/**
 * Records were included in Java 16. An immutable class to transport data: you only
 * declare the components and the compiler generates the constructor with all fields,
 * an accessor for every field (called value(), not getValue(), without the get prefix),
 * equals(), hashCode() and toString().
 */
public record Odds(double value) {
    public Odds {
        if (value <= 1.0) {
            throw new IllegalArgumentException("Odds can't be less than 1.0, got: " + value);
        }
    }
}

