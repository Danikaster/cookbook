package com.cookbook.cookbook.services;

import com.cookbook.cookbook.service.CounterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CounterServiceTest {

    private CounterService counterService;

    @BeforeEach
    void setUp() {
        counterService = new CounterService();
    }

    @Test
    void testIncrement() {
        counterService.increment();
        counterService.increment();
        Assertions.assertEquals(2, counterService.get());
    }

    @Test
    void testGet() {
        Assertions.assertEquals(0, counterService.get());
    }

    @Test
    void testIncrementAndGet() {
        counterService.increment();
        Assertions.assertEquals(1, counterService.get());
    }
}
