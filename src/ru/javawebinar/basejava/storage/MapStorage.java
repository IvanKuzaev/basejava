package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {

    protected HashMap<String, Resume> storage;

    public MapStorage() {
        storage = new HashMap<String, Resume>();;
    }

    protected boolean resumeExist(String uuid) {
        return storage.containsKey(uuid);
    }

    protected void clearIntern() {
        storage.clear();
    }

    protected void updateIntern(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    protected void saveIntern(Resume resume) {
        updateIntern(resume);
    }

    protected Resume getIntern(String uuid) {
        return storage.get(uuid);
    }

    protected void deleteIntern(String uuid) {
        storage.remove(uuid);
    }

    public Resume[] getAll() {
        return storage.values().toArray(new Resume[1]);
    }

}
