package com.cookbook.cookbook.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EntityCache<Key, Value> {
    private final Map<Key, Value> cache;
    private static final int MAX_SIZE = 100;

    public EntityCache() {
        this.cache = new HashMap<>();
    }

    public void put(Key key, Value value) {
        if (cache.size() >= MAX_SIZE) {
            cache.clear();
        }
        cache.put(key, value);
    }

    public Value get(Key key) {
        return cache.get(key);
    }

    public void remove(Key key) {
        cache.remove(key);
    }


}
