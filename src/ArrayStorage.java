/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    private int size;

    void clear() {
        storage = null;
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size += 1;
    }

    private int find(String uuid) {
        int r = -1;
        for (int i = 0; i < size; i += 1)
            if (storage[i].uuid.equals(uuid)) {
                r = i;
                break;
            }
        return r;
    }

    private void shift(int i) {
        for (; storage[i+1] != null; i += 1)
            storage[i] = storage[i+1];
    }

    Resume get(String uuid) {
        int i = find(uuid);
        if (i > -1)
            return storage[i];
        else
            return null;
    }

    void delete(String uuid) {
        int i = find(uuid);
        if (i > -1) {
            //storage[i] = null;
            shift(i);
            size -= 1;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[size];
        for(int i = 0; i < size; i += 1)
            all[i] = storage[i];
        return all;
    }

    int size() {
        return size;
    }
}
