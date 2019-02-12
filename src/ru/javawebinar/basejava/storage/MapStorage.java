package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isResumeExist(Object key) {
        return storage.containsKey((String)key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateInternal(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected void saveInternal(Resume resume, Object key) {
        updateInternal(resume, key);
    }

    @Override
    protected Resume getInternal(Object key) {
        return storage.get((String)key);
    }

    @Override
    protected void deleteInternal(Object key) {
        storage.remove((String)key);
    }

    @Override
    protected Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

}
