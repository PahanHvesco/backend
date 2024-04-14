package com.myapp.backend.component;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleCacheComponentTest {

    private SimpleCacheComponent<String> cache;

    @BeforeEach
    public void setUp() {
        cache = new SimpleCacheComponent<>();
    }

    @Test
    public void testPutAndGet() {
        long key = 123;
        String value = "Test Value";

        cache.put(key, value);

        assertEquals(value, cache.get(key));
    }

    @Test
    public void testRemove() {
        long key = 123;
        String value = "Test Value";

        cache.put(key, value);
        cache.remove(key);

        assertNull(cache.get(key));
    }

    @Test
    public void testClearCacheWhenFull() {
        // Add more than 100 items to fill the cache
        for (long i = 0; i < 110; i++) {
            cache.put(i, "Value " + i);
        }

        // Cache should be cleared after exceeding the size limit
        assertNull(cache.get(0));
        assertNull(cache.get(99));
        assertNull(cache.get(100));
    }
}
