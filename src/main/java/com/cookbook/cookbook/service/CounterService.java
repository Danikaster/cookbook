package com.cookbook.cookbook.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CounterService {
    AtomicInteger requestCounter = new AtomicInteger(0);

    public void increment() {
        requestCounter.incrementAndGet();
    }

    public int get() {
        return requestCounter.get();
    }
}
