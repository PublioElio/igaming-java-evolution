package com.adrianodiaz.igamingjavaevolution.betengine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OddsTest {
    @Test
    void createValidOdds() {
        var odds = new Odds(2.5);
        assertEquals(2.5, odds.value(), 0.0001);
    }

    @Test
    void rejectOddsEqualsToOne() {
        assertThrows(IllegalArgumentException.class, () -> new Odds(1.0));
    }

    @Test
    void rejectOddsBelowOne() {
        assertThrows(IllegalArgumentException.class, () -> new Odds(0.5));
    }
}
