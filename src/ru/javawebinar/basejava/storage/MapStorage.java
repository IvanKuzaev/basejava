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
    protected boolean resumeExist(Object key) {
        return storage.containsKey(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateIntern(Resume resume, Object key) {
        storage.put((String)key, resume);
    }

    @Override
    protected void saveIntern(Resume resume, Object key) {
        updateIntern(resume, key);
    }

    @Override
    protected Resume getIntern(Object key) {
        return storage.get(key);
    }

    @Override
    protected void deleteIntern(Object key) {
        storage.remove(key);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

}
