package ru.javawebinar.basejava.storage;

import org.junit.After;

public class AbstractDiskStorageTest extends AbstractStorageTest {

    public AbstractDiskStorageTest(Storage storage) {
        super(storage);
    }

    @After
    public void cleanUp() {
        storage.clear();
    }

}
