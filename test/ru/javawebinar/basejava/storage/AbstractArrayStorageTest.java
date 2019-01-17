package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("dummy" + i));
            }
        } catch (StorageException e) {
            Assert.fail("Testing method save(): inappropriate storage overflow.");
        }
        storage.save(RESUME_NEW);
    }

}
