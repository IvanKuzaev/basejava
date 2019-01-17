package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedList;

public class ListStorage extends AbstractStorage {

    protected LinkedList<Resume> storage = new LinkedList();

    private int getIndex(String uuid) {
        for(Resume resume : storage) {
            if (uuid.equals(resume.getUuid()))
                return storage.indexOf(resume);
        }
        return -1;
    }

    protected boolean resumeExist(String uuid) {
        return getIndex(uuid) >= 0 ? true : false;
    }

    protected void clearIntern() {
        storage.clear();
    }

    protected void updateIntern(Resume resume) {
        storage.set(getIndex(resume.getUuid()), resume);
    }

    protected void saveIntern(Resume resume) {
        storage.add(resume);
    }

    protected Resume getIntern(String uuid) {
        return storage.get(getIndex(uuid));
    }

    protected void deleteIntern(String uuid) {
        storage.remove(getIndex(uuid));
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[1]);
    }

}
