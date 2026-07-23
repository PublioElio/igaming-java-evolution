package com.adrianodiaz.igamingjavaevolution.betengine;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OddsTest {
    @Test
    void createValidOdds() {
        var odds = new Odds(new BigDecimal("2.5"));
        assertEquals(0, odds.value().compareTo(new BigDecimal("2.5")));
    }

    @Test
    void rejectOddsEqualsToOne() {
        assertThrows(IllegalArgumentException.class, () -> new Odds(BigDecimal.ONE));
    }

    @Test
    void rejectOddsBelowOne() {
        var invalidOdds = new BigDecimal("0.5");
        assertThrows(IllegalArgumentException.class, () -> new Odds(invalidOdds));
    }
}
