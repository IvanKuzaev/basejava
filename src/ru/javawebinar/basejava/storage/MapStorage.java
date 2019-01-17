package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {

    protected HashMap<String, Resume> storage = new HashMap<String, Resume>();

    @Override
    protected boolean resumeExist(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected void clearIntern() {
        storage.clear();
    }

    @Override
    protected void updateIntern(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void saveIntern(Resume resume) {
        updateIntern(resume);
    }

    @Override
    protected Resume getIntern(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteIntern(String uuid) {
        storage.remove(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[1]);
    }

}
