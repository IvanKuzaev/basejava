package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapStorageRemake extends AbstractMapStorage<MapStorageRemake.MapEntry> {

    class MapEntry {
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
    protected boolean isResumeExist(MapEntry key) {
        return key.isExist;
    }

    @Override
    protected void updateInternal(Resume resume, MapEntry key) {
        storage.put(key.key, resume);
    }

    @Override
    protected Resume getInternal(MapEntry key) {
        return key.value;
    }

    @Override
    protected void deleteInternal(MapEntry key) {
        storage.remove(key.key);
    }

}
