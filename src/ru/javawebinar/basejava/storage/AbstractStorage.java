package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isResumeExist(SK key);

    public void update(Resume resume) {
        SK key = getExistedKey(resume.getUuid());
        updateInternal(resume, key);
    }

    protected abstract void updateInternal(Resume resume, SK key);

    public void save(Resume resume) {
        SK key = getNotExistedKey(resume.getUuid());
        saveInternal(resume, key);
    }

    protected abstract void saveInternal(Resume resume, SK key);

    public Resume get(String uuid) {
        SK key = getExistedKey(uuid);
        return getInternal(key);
    }

    protected abstract Resume getInternal(SK key);

    public void delete(String uuid) {
        SK key = getExistedKey(uuid);
        deleteInternal(key);
    }

    protected abstract void deleteInternal(SK key);

    protected abstract Resume[] getAll();

    //return list, sorted by fullName
    public List<Resume> getAllSorted() {
        Resume[] resumes = getAll();
        Arrays.sort(resumes, RESUME_COMPARATOR);
        return Arrays.asList(resumes);
    }

    private SK getExistedKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (!isResumeExist(key)) {
            throw new NotExistStorageException(uuid);
        } else {
            return key;
        }
    }

    private SK getNotExistedKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (isResumeExist(key)) {
            throw new ExistStorageException(uuid);
        } else {
            return key;
        }
    }

}
