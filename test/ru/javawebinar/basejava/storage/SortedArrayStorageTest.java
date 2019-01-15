package ru.javawebinar.basejava.storage;

import java.util.Arrays;

public class SortedArrayStorageTest extends AbstractArrayStorageTest{

    public SortedArrayStorageTest() {
        storage = new SortedArrayStorage();
        Arrays.sort(RESUMES);
    }

}
