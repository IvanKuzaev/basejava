package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage  implements Storage {
    protected static final int STORAGE_LIMIT = 5;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    ////ABSTRACT METHODS
    /**
     * inserts an element into the storage
     * @param resume resume to insert
     * @param index the result of getIndex(r), implementation dependent
     */
    protected abstract void insertElement(Resume resume, int index);

    /**
     * deletes an element at the specified index in the storage
     * @param index index of an element, must be from 0 to size-1
     */
    protected abstract void deleteElement(int index);

    /**
     * @return int, index in storage of found resume, if no resume found, return value is implementation dependent
     */
    protected abstract int getIndex(String uuid);

    //CONCRETE METHODS
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            if (size < STORAGE_LIMIT) {
                insertElement(resume, index);
                size++;
            } else {
                System.out.println("save() method: storage array overflow.");
            }
        } else {
            System.out.println("save() method: resume " + resume.getUuid() + " is in database, use update() method.");
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            storage[index] = resume;
        } else {
            System.out.println("update() method: resume " + resume.getUuid() + " not found.");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            return storage[index];
        } else {
            System.out.println("get() method: resume " + uuid + " not found.");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            if (index < size - 1) {
                deleteElement(index);
            }
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("delete() method: resume " + uuid + " not found.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

}

