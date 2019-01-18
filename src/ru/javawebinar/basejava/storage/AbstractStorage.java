package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean resumeExist(Object key);

    public abstract void clear();

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Object key = getSearchKey(uuid);
        if (resumeExist(key)) {
            updateIntern(resume, key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void updateIntern(Resume resume, Object key);

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Object key = getSearchKey(uuid);
        if (!resumeExist(key)) {
            saveIntern(resume, key);
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract void saveIntern(Resume resume, Object key);

    public Resume get(String uuid) {
        Object key = getSearchKey(uuid);
        if (resumeExist(key)) {
            return getIntern(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract Resume getIntern(Object key);

    public void delete(String uuid) {
        Object key = getSearchKey(uuid);
        if (resumeExist(key)) {
            deleteIntern(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void deleteIntern(Object key);

    public abstract Resume[] getAll();

    public abstract int size();

}
