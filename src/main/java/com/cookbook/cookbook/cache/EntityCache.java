package com.cookbook.cookbook.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EntityCache<KEY, VALUE> {
    private final Map<KEY, VALUE> cache;
    private static final int MAX_SIZE = 100;

    public EntityCache() {
        this.cache = new HashMap<>();
    }

    public void put(KEY key, VALUE value) {
        if (cache.size() >= MAX_SIZE) {
            cache.clear();
        }
        cache.put(key, value);
    }

    public VALUE get(KEY key) {
        return cache.get(key);
    }

    public void remove(KEY key) {
        cache.remove(key);
    }


}
