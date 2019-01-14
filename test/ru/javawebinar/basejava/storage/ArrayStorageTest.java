package ru.javawebinar.basejava.storage;

import org.junit.BeforeClass;

public class ArrayStorageTest extends AbstractArrayStorageTest{

    public ArrayStorageTest() { storage = new ArrayStorage(); }

    @BeforeClass
    public static void info() {
        System.out.println("Unit testing of ArrayStorage class.");
    }

}
