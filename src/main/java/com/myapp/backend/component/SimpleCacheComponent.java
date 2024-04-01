package com.myapp.backend.component;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class SimpleCacheComponent<T> {
    private final Map<Long, T> cache;

    public SimpleCacheComponent() {
        this.cache = new ConcurrentHashMap<>();
    }

    public void put(final long keyHash, final T value) {
        final int size = 100;
        if (cache.size() <= size) {
            cache.put(keyHash, value);
        } else {
            cache.clear();
        }
    }

    public T get(final long keyHash) {
        return cache.get(keyHash);
    }

    public void remove(final long key) {
        cache.remove(key);
    }
}
