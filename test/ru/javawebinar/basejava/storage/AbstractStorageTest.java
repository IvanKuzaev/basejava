package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public abstract class AbstractStorageTest {

    protected static final String UUID_01 = "uuid04";
    protected static final String UUID_02 = "uuid03";
    protected static final String UUID_03 = "uuid02";
    protected static final String UUID_04 = "uuid01";

    protected static final Resume RESUME_1 = new Resume(UUID_01);
    protected static final Resume RESUME_2 = new Resume(UUID_02);
    protected static final Resume RESUME_3 = new Resume(UUID_03);
    protected static final Resume RESUME_4 = new Resume(UUID_04);

    protected static final String UUID_NEW = "uuid_new";
    protected static final Resume RESUME_NEW = new Resume(UUID_NEW);

    protected static final Resume[] RESUMES = { RESUME_1, RESUME_2, RESUME_3, RESUME_4 };
    protected static final int SIZE = RESUMES.length;

    protected static final Resume RESUME_EXIST = RESUMES[0];
    protected static final String UUID_EXIST = RESUME_EXIST.getUuid();

    protected Storage storage = null;

    @Before
    public void setUp() throws Exception {
        storage.clear();
        for (int i = 0; i < SIZE; i++) {
            storage.save(RESUMES[i]);
        }
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals("Testing method clear(): size equals non zero.", 0, storage.size());
    }

    @Test
    public void save() throws Exception {
        int sizeBefore = storage.size();
        storage.save(RESUME_NEW);
        Assert.assertEquals("Testing method save(): size didn't increment.", sizeBefore + 1, storage.size());
        Assert.assertSame("Testing method save(): invalid Resume reference.", RESUME_NEW, storage.get(UUID_NEW));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_EXIST);
    }

    @Test
    public void update() throws Exception {
        Resume resumeUpdated = new Resume(UUID_EXIST);
        storage.update(resumeUpdated);
        Assert.assertSame("Testing method update(): invalid Resume reference.", resumeUpdated, storage.get(UUID_EXIST));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_NEW);
    }

    @Test
    public void get() throws Exception {
        Assert.assertSame("Testing method get(): invalid Resume reference.", RESUME_EXIST, storage.get(UUID_EXIST));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_NEW);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        int sizeBefore = storage.size();
        storage.delete(UUID_EXIST);
        Assert.assertEquals("Testing method delete(): size didn't decrement.", sizeBefore, storage.size() + 1);
        storage.delete(UUID_EXIST);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_NEW);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes = storage.getAll();
        Arrays.sort(resumes);
        Arrays.sort(RESUMES);
        Assert.assertArrayEquals("Testing method getAll(): elements are not identical.", RESUMES, resumes);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals("Testing method size(): invalid size value.", SIZE, storage.size());
    }

}
