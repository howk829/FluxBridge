package com.tho.fluxbridge.common.repo;

import lombok.extern.slf4j.Slf4j;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import net.openhft.chronicle.map.MapEntry;

import java.io.File;

@Slf4j
public class KeyValueStoreImpl implements KeyValueStore {

    protected ChronicleMap<String, Long> keyValueStore = null;

    public KeyValueStoreImpl(String storeName, String homePath) {

        String storeFile = String.format("%s%sdata%s%s", homePath, File.separator, File.separator, storeName);


        try {
            keyValueStore = ChronicleMapBuilder.of(String.class, Long.class)
                    .name("key-value-store")
                    .entries(100)
                    .averageKeySize(10)
                    .putReturnsNull(true)

                    // recovery mode is not recommended
                    .createPersistedTo(new File(storeFile));
        } catch (Exception e) {
            log.error("Error recovering key/Value store ", e);
        }

    }

    @Override
    public void putValue(String key, long val) {
        keyValueStore.put(key, val);
    }

    @Override
    public long incValue(String key, long def) {

        if (!keyValueStore.containsKey(key)) {
            putValue(key, def);
            return def;
        }
        long next = keyValueStore.get(key) + 1;

        //keyValueStore.put(key, next);
        var context = keyValueStore.queryContext(key);
        Long result = null;
        context.updateLock().lock();
        try {
            MapEntry<String, Long> e = context.entry();
            result = e.value().get() + 1;
            e.doReplaceValue(context.wrapValueAsData(result));
        } finally {
            context.close();
        }
        return next;

    }

    @Override
    public long getOrDef(String key, long def) {
        return keyValueStore.getOrDefault(key, def);
    }

    @Override
    public void deleteValue(String key) {
        keyValueStore.remove(key);
    }
}
