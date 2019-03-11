package ru.javawebinar.basejava.storage;

import org.junit.After;

public class AbstractFileStorageTest extends AbstractStorageTest {

    public AbstractFileStorageTest(Storage storage) {
        super(storage);
    }

    @After
    public void shutDown() throws Exception {
        storage.clear();
    }

}