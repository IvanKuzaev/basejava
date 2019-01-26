package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

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
    protected boolean isResumeExist(Object key) {
        return ((MapEntry) key).isExist;
    }

    @Override
    protected void updateInternal(Resume resume, Object key) {
        storage.put(((MapEntry) key).key, resume);
    }

    @Override
    protected Resume getInternal(Object key) {
        return ((MapEntry) key).value;
    }

    @Override
    protected void deleteInternal(Object key) {
        storage.remove(((MapEntry) key).key);
    }

}
