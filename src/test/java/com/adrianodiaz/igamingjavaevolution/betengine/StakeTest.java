package com.adrianodiaz.igamingjavaevolution.betengine;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StakeTest {
    @Test
    void createValidStake() {
        var stake = new Stake(new BigDecimal("3.5"));
        assertEquals(0, stake.amount().compareTo(new BigDecimal("3.5")));
    }

    @Test
    void rejectZeroStake() {
        var zero = BigDecimal.ZERO;
        assertThrows(IllegalArgumentException.class, () -> new Stake(zero));
    }

    @Test
    void rejectNegativeStake() {
        var negative = new BigDecimal("-5.00");
        assertThrows(IllegalArgumentException.class, () -> new Stake(negative));
    }

    @Test
    void rejectNullStake() {
        assertThrows(IllegalArgumentException.class, () -> new Stake(null));
    }
}
