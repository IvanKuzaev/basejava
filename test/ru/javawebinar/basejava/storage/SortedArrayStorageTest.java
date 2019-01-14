package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorageTest extends AbstractArrayStorageTest{

    public SortedArrayStorageTest() {
        storage = new SortedArrayStorage();
    }

    protected void prepareElements(Resume[] resumes) { Arrays.sort(resumes); }

}