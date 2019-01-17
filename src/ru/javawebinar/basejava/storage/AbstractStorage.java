package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected int size = 0;

    protected abstract boolean resumeExist(String uuid);

    protected abstract void clearIntern();

    public void clear() {
        clearIntern();
        size = 0;
    }

    protected abstract void updateIntern(Resume resume);

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        if (resumeExist(uuid)) {
            updateIntern(resume);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void saveIntern(Resume resume);

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (!resumeExist(uuid)) {
            saveIntern(resume);
            size++;
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract Resume getIntern(String uuid);

    public Resume get(String uuid) {
        if (resumeExist(uuid)) {
            return getIntern(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void deleteIntern(String uuid);

    public void delete(String uuid) {
        if (resumeExist(uuid)) {
            deleteIntern(uuid);
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public abstract Resume[] getAll();

    public int size() {
        return size;
    }

}
