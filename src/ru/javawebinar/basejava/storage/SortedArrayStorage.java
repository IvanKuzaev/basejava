package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    protected void insertElement(Resume resume, int index) {
        index = -(index + 1);
        System.arraycopy(storage, index, storage, index+1, size-index);
        storage[index] = resume;
    }

    protected void deleteElement(int index) {
        System.arraycopy(storage, index+1, storage, index, size-1-index);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
