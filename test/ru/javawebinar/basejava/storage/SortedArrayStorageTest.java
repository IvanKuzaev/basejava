package ru.javawebinar.basejava.storage;

import org.junit.BeforeClass;

public class SortedArrayStorageTest extends AbstractArrayStorageTest{

    public SortedArrayStorageTest() {
        storage = new SortedArrayStorage();
    }

    @BeforeClass
    public static void info() {
        System.out.println("Unit testing of SortedArrayStorage class.");
    }

}