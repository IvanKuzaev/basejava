/**
 * Array based storage for Resumes
 */
import java.util.Arrays;

public class ArrayStorage {

    static private final int MAX = 10000;

    Resume[] storage = new Resume[MAX];

    private int size;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    private int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    void save(Resume r) {
        int i = find(r.uuid);
        if (i == -1) {
            if (size < MAX) {
                storage[size] = r;
                size++;
            }
            else {
                System.out.println("save() method: storage array overflow.");
            }
        }
        else {
            System.out.println("save() method: resume "+r.uuid+" is in database, use update() method.");
        }
    }

    private void shift(int i) {
        for (; storage[i + 1] != null; i++) {
            storage[i] = storage[i + 1];
        }
    }

    void update(Resume r) {
        int i = find(r.uuid);
        if (i > -1) {
            storage[i] = r;
        }
        else {
            System.out.println("update() method: resume "+r.uuid+" not found.");
        }
    }

    Resume get(String uuid) {
        int i = find(uuid);
        if (i > -1) {
            return storage[i];
        }
        else {
            System.out.println("get() method: resume "+uuid+" not found.");
            return null;
        }
    }

    void delete(String uuid) {
        int i = find(uuid);
        if (i > -1) {
            shift(i);
            size--;
        }
        else {
            System.out.println("delete() method: resume "+uuid+" not found.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.<Resume>copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
