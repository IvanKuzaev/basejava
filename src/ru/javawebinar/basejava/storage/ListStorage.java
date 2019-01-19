package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        for (int index = 0; index < storage.size(); index++) {
            Resume resume = storage.get(index);
            if (uuid.equals(resume.getUuid()))
                return index;

        }
        return -1;
    }

    @Override
    protected boolean resumeExist(Object key) {
        int index = (int)key;
        return index >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateIntern(Resume resume, Object key) {
        int index = (int)key;
        storage.set(index, resume);
    }

    @Override
    protected void saveIntern(Resume resume, Object key) {
        storage.add(resume);
    }

    @Override
    protected Resume getIntern(Object key) {
        int index = (int)key;
        return storage.get(index);
    }

    @Override
    protected void deleteIntern(Object key) {
        int index = (int)key;
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

}
