package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{
//    @Override
    public void clear() {

    }

//    @Override
    public void update(Resume r) {

    }

//    @Override
    public void save(Resume r) {

    }

//    @Override
    public void delete(String uuid) {

    }

//    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

//    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    /**
     * shifts a subarray of storage
     * @param s shift distance in elements
     *          by s > 0 shifts to the right, opening a gap
     *          by s < 0 shifts to the left, overwriting elements
     * @param i the index of the subarray's start element
     */
    private void shift(int s, int i) {

    }
}
