package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

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
    protected boolean isResumeExist(Integer index) {
        return (index >= 0);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateInternal(Resume resume, Integer index) {
        storage.set(index, resume);
    }

    @Override
    protected void saveInternal(Resume resume, Integer index) {
        storage.add(resume);
    }

    @Override
    protected Resume getInternal(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void deleteInternal(Integer index) {
        storage.remove((int)index);//without (int) didn't pass test. don't know why...
    }

    @Override
    protected Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

}
