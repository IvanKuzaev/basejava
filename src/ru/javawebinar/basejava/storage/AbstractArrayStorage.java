package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage  implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    /**
     * clears storage by erasing all object references
     */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * inserts an element into the storage
     * @param r resume to insert
     * @param index the result of getIndex(r), implementation dependent
     */
    protected abstract void insertElement(Resume r, int index);

    /**
     * adds a new resume to the and of storage
     */
    public void save(Resume r) {
        int i = getIndex(r.getUuid());
        if (i < 0) {
            if (size < STORAGE_LIMIT) {
                insertElement(r, i);
                size++;
            } else {
                System.out.println("save() method: storage array overflow.");
            }
        } else {
            System.out.println("save() method: resume " + r.getUuid() + " is in database, use update() method.");
        }
    }

    /**
     * replaces a resume in storage with a new one with the same uuid
     */
    public void update(Resume r) {
        int i = getIndex(r.getUuid());
        if (i > -1) {
            storage[i] = r;
        } else {
            System.out.println("update() method: resume " + r.getUuid() + " not found.");
        }
    }

    /**
     * @return Resume, instance of Resume with specified uuid, if not found, returns null
     */
    public Resume get(String uuid) {
        int i = getIndex(uuid);
        if (i > -1) {
            return storage[i];
        } else {
            System.out.println("get() method: resume " + uuid + " not found.");
            return null;
        }
    }

    /**
     * deletes an element at the specified index in the storage
     * @param index index of an element, must be from 0 to size-1
     */
    protected abstract void deleteElement(int index);

    /**
     * deletes resume with particular uuid if found, doesn't preserve order of elements
     */
    public void delete(String uuid) {
        int i = getIndex(uuid);
        if (i > -1) {
            if (i < size - 1) {
                deleteElement(i);
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

    /**
     * @return int, amount of resumes in storage, also the next free index to fill
     */
    public int size() {
        return size;
    }

    /**
     * @return int, index in storage of found resume, if no resume found, return value is implementation dependent
     */
    protected abstract int getIndex(String uuid);

}

