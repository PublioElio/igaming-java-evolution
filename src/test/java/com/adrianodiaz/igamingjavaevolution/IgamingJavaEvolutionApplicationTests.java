package com.adrianodiaz.igamingjavaevolution;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class IgamingJavaEvolutionApplicationTests {

    @Test
    void mainRunsWithoutThrowing() {
        assertDoesNotThrow(() -> IgamingJavaEvolutionApplication.main(new String[0]));
    }

}
