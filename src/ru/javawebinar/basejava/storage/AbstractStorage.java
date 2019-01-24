package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean resumeExist(Object key);

    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume r1, Resume r2) {
            int cName = r1.getFullName().compareTo(r2.getFullName());
            if (cName != 0) {
                return cName;
            } else {
                return r1.getUuid().compareTo(r2.getUuid());
            }
        }
    };

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

    protected abstract Resume[] getAll();

    //return list, sorted by fullName
    public List<Resume> getAllSorted() {
        Resume[] resumes = getAll();
        Arrays.sort(resumes, RESUME_COMPARATOR);
        return Arrays.asList(resumes);
    }


    public abstract int size();

}
