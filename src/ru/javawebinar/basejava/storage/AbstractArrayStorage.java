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

    protected int size = 0;

    /**
     * inserts an element into the storage
     *
     * @param resume resume to insert
     * @param index  the result of getIndex(r), implementation dependent
     */
    protected abstract void insertElement(Resume resume, int index);

    /**
     * deletes an element at the specified indexIntern in the storage
     * @param index index of an element, must be from 0 to size-1
     */
    protected abstract void deleteElement(int index);

    @Override
    protected boolean isResumeExist(Object index) {
        return (int)index >= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void saveInternal(Resume resume, Object index) {
        if (size < STORAGE_LIMIT) {
            insertElement(resume, (int)index);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    public void updateInternal(Resume resume, Object index) {
        storage[(int)index] = resume;
    }

    @Override
    public Resume getInternal(Object key) {
        int index = (int)key;
        return storage[index];
    }

    @Override
    public void deleteInternal(Object index) {
        if ((int)index < size - 1) {
            deleteElement((int)index);
        }
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    protected Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

}
