package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapStorage extends AbstractMapStorage<String> {

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isResumeExist(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected void updateInternal(Resume resume, String key) {
        storage.put(key, resume);
    }

    @Override
    protected Resume getInternal(String key) {
        return storage.get(key);
    }

    @Override
    protected void deleteInternal(String key) {
        storage.remove(key);
    }

}
