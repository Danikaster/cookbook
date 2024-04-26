package com.cookbook.cookbook.services;

import com.cookbook.cookbook.service.CounterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CounterServiceTest {

    private CounterService counterService;

    @BeforeEach
    public void setUp() {
        counterService = new CounterService();
    }

    @Test
    public void testIncrement() {
        counterService.increment();
        counterService.increment();
        Assertions.assertEquals(2, counterService.get());
    }

    @Test
    public void testGet() {
        Assertions.assertEquals(0, counterService.get());
    }

    @Test
    public void testIncrementAndGet() {
        counterService.increment();
        Assertions.assertEquals(1, counterService.get());
    }
}
