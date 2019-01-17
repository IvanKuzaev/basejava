package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.exception.*;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    private int indexIntern;//saves the result of last getIndex() call, invoked in resumeExist() method; non direct value transfer for *Intern methods

    /**
     * inserts an element into the storage
     *
     * @param resume resume to insert
     * @param index  the result of getIndex(r), implementation dependent
     */
    protected abstract void insertElement(Resume resume, int index);

    /**
     * deletes an element at the specified indexIntern in the storage
     * @param index indexIntern of an element, must be from 0 to size-1
     */
    protected abstract void deleteElement(int index);

    /**
     * @return int, indexIntern in storage of found resume, if no resume found, return value is implementation dependent
     */
    protected abstract int getIndex(String uuid);

    protected boolean resumeExist(String uuid) {
        indexIntern = getIndex(uuid);
        if (indexIntern >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void clearIntern() {
        Arrays.fill(storage, 0, size, null);
    }

    public void saveIntern(Resume resume) {
        if (size < STORAGE_LIMIT) {
            insertElement(resume, indexIntern);
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    public void updateIntern(Resume resume) {
        storage[indexIntern] = resume;
    }

    public Resume getIntern(String uuid) {
        return storage[indexIntern];
    }

    public void deleteIntern(String uuid) {
        if (indexIntern < size - 1) {
            deleteElement(indexIntern);
        }
        storage[size - 1] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

}
