package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            Resume resume = storage.get(i);
            if (uuid.equals(resume.getUuid()))
                return i;

        }
        return -1;
    }

    @Override
    protected boolean isResumeExist(Object index) {
        return (int)index >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateInternal(Resume resume, Object index) {
        storage.set((int)index, resume);
    }

    @Override
    protected void saveInternal(Resume resume, Object key) {
        storage.add(resume);
    }

    @Override
    protected Resume getInternal(Object index) {
        return storage.get((int)index);
    }

    @Override
    protected void deleteInternal(Object index) {
        storage.remove((int)index);
    }

    @Override
    protected Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

}
