package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapStorage<E> extends AbstractStorage<E> {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void saveInternal(Resume resume, E key) {
        updateInternal(resume, key);
    }

    @Override
    public void clear() {
        storage.clear();
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
