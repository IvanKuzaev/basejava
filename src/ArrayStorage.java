/**
 * Array based storage for Resumes
 */

import java.util.Arrays;

public class ArrayStorage {

    static private final int MAX = 10_000;

    Resume[] storage = new Resume[MAX];

    private int size;

    /**
     * clears storage by erasing all object references
     */
    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * adds a new resume to the and of storage
     */
    void save(Resume r) {
        int i = getIndex(r.getUuid());
        if (i == -1) {
            if (size < MAX) {
                storage[size] = r;
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
    void update(Resume r) {
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
    Resume get(String uuid) {
        int i = getIndex(uuid);
        if (i > -1) {
            return storage[i];
        } else {
            System.out.println("get() method: resume " + uuid + " not found.");
            return null;
        }
    }

    /**
     * deletes resume with particular uuid if found, doesn't preserve order of elements
     */
    void delete(String uuid) {
        int i = getIndex(uuid);
        if (i > -1) {
            if (i < size - 1) {
                storage[i] = storage[size - 1];
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
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return int, amount of resumes in storage, also the next free index to fill
     */
    int size() {
        return size;
    }

    /**
     * @return int, index in storage of found resume, if no resume found, returns -1
     */
    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
