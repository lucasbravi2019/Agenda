package com.bravi.agenda.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {

    private final Map<K, V> map;

    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    public static <K, V> MapBuilder<K, V> newMap(K key, V value) {
        return Builder.newMap(key, value);
    }

    public MapBuilder<K, V> add(K key, V value) {
        return Builder.add(this, key, value);
    }

    public Map<K, V> get() {
        return map;
    }

    private static class Builder {

        public static <K, V> MapBuilder<K, V> newMap(K key, V value) {
            Map<K, V> map = new HashMap<>();
            map.put(key, value);
            return new MapBuilder<>(map);
        }

        public static <K, V> MapBuilder<K, V> add(MapBuilder<K, V> builder, K key, V value) {
            builder.get().put(key, value);
            return builder;
        }

    }


}
