/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    static private final int MAX = 10000;

    Resume[] storage = new Resume[MAX];

    private int size;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    private int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    private void shift(int i) {
        for (; storage[i + 1] != null; i++) {
            storage[i] = storage[i + 1];
        }
    }

    Resume get(String uuid) {
        int i = find(uuid);
        if (i > -1) {
            return storage[i];
        }
        else {
            return null;
        }
    }

    void delete(String uuid) {
        int i = find(uuid);
        if (i > -1) {
            shift(i);
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[size];
        for (int i = 0; i < size; i++) {
            all[i] = storage[i];
        }
        return all;
    }

    int size() {
        return size;
    }
}
