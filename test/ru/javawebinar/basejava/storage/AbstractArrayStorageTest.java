package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("John Doe", "dummy" + i));
            }
        } catch (StorageException e) {
            Assert.fail("Testing method save(): inappropriate storage overflow.");
        }
        storage.save(new Resume("John New"));
    }

}
