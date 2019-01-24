package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorageRemake extends MapStorage {

    private class MapEntry {
        String key;
        Resume value;
        boolean isExist;
        MapEntry(String key) {
            this.key = key;
            this.value = storage.get(key);
            this.isExist = storage.containsKey(key);
        }
    }

    @Override
    protected MapEntry getSearchKey(String uuid) {
        return new MapEntry(uuid);
    }

    @Override
    protected boolean resumeExist(Object key) {
        return ((MapEntry)key).isExist;
    }

    @Override
    protected void updateIntern(Resume resume, Object key) {
        storage.put(((MapEntry)key).key, resume);
    }

    @Override
    protected Resume getIntern(Object key) {
        return ((MapEntry)key).value;
    }

    @Override
    protected void deleteIntern(Object key) {
        storage.remove(((MapEntry)key).key);
    }

}
