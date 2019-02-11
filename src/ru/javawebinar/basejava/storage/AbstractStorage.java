package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isResumeExist(Object key);

    public void update(Resume resume) {
        Object key = getExistedKey(resume.getUuid());
        updateInternal(resume, key);
    }

    protected abstract void updateInternal(Resume resume, Object key);

    public void save(Resume resume) {
        Object key = getNotExistedKey(resume.getUuid());
        saveInternal(resume, key);
    }

    protected abstract void saveInternal(Resume resume, Object key);

    public Resume get(String uuid) {
        Object key = getExistedKey(uuid);
        return getInternal(key);
    }

    protected abstract Resume getInternal(Object key);

    public void delete(String uuid) {
        Object key = getExistedKey(uuid);
        deleteInternal(key);
    }

    protected abstract void deleteInternal(Object key);

    protected abstract Resume[] getAll();

    //return list, sorted by fullName
    public List<Resume> getAllSorted() {
        Resume[] resumes = getAll();
        Arrays.sort(resumes, Resume.COMPARATOR);
        return Arrays.asList(resumes);
    }

    private Object getExistedKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (!isResumeExist(key)) {
            throw new NotExistStorageException(uuid);
        } else {
            return key;
        }
    }

    private Object getNotExistedKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (isResumeExist(key)) {
            throw new ExistStorageException(uuid);
        } else {
            return key;
        }
    }

}
