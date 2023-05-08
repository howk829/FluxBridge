package com.tho.fluxbridge.common.repo;

public interface KeyValueStore {

    void putValue(String key, long val);

    long incValue(String key, long def);

    long getOrDef(String key, long def);

    void deleteValue(String key);

}
