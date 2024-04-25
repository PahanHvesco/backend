package com.myapp.backend.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RequestCounterService {
    private final AtomicInteger counter = new AtomicInteger(0);

    public synchronized int incrementAndGet() {
        return counter.incrementAndGet();
    }
}
