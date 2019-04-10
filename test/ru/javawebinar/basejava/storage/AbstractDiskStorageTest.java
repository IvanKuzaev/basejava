package ru.javawebinar.basejava.storage;

import org.junit.After;
import ru.javawebinar.basejava.Config;

public class AbstractDiskStorageTest extends AbstractStorageTest {
    protected static final String DIRECTORY = Config.storageDir;

    public AbstractDiskStorageTest(Storage storage) {
        super(storage);
    }

    @After
    public void cleanUp() {
        storage.clear();
    }

}
