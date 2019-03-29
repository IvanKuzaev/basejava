package ru.javawebinar.basejava.storage;

import org.junit.After;

public class AbstractDiskStorageTest extends AbstractStorageTest {
    protected static final String DIRECTORY = "C:\\Users\\Ivan\\IntelliJ Projects\\basejava\\data";

    public AbstractDiskStorageTest(Storage storage) {
        super(storage);
    }

    @After
    public void cleanUp() {
        storage.clear();
    }

}
