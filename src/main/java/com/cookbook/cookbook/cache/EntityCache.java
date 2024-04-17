package com.cookbook.cookbook.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EntityCache<K, V> {
    private final Map<K, V> cache;
    private static final int MAX_SIZE = 100;

    public EntityCache() {
        this.cache = new HashMap<>();
    }

    public void put(K key, V value) {
        if (cache.size() >= MAX_SIZE) {
            cache.clear();
        }
        cache.put(key, value);
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void remove(K key) {
        cache.remove(key);
    }

}
