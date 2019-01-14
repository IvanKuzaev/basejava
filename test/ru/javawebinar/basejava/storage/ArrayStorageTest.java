package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorageTest extends AbstractArrayStorageTest{

    public ArrayStorageTest() { storage = new ArrayStorage(); }

    protected void prepareElements(Resume[] resumes) { }

}
