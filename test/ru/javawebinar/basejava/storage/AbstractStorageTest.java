package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {

    private static final String UUID_01 = "uuid01";
    private static final String FULLNAME_01 = "Ivanov Sergei";
    private static final String UUID_02 = "uuid02";
    private static final String FULLNAME_02 = "Petrov Vladimir";
    private static final String UUID_03 = "uuid03";
    private static final String FULLNAME_03 = "Petrov Vladimir";
    private static final String UUID_04 = "uuid04";
    private static final String FULLNAME_04 = "Sidorova Elena ";

    private static final Resume RESUME_1 = new Resume(FULLNAME_01, UUID_01);
    private static final Resume RESUME_2 = new Resume(FULLNAME_02, UUID_02);
    private static final Resume RESUME_3 = new Resume(FULLNAME_03, UUID_03);
    private static final Resume RESUME_4 = new Resume(FULLNAME_04, UUID_04);

    private static final String UUID_NEW = "uuid_new";
    private static final String FULLNAME_NEW = "John Smith";
    private static final Resume RESUME_NEW = new Resume(FULLNAME_NEW, UUID_NEW);

    private static final Resume[] RESUMES = { RESUME_4, RESUME_3, RESUME_2, RESUME_1 };
    private static final int SIZE = RESUMES.length;

    private static final Resume RESUME_EXIST = RESUMES[0];
    private static final String UUID_EXIST = RESUME_EXIST.getUuid();
    private static final String FULLNAME_EXIST = RESUME_EXIST.getFullName();

    protected Storage storage = null;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private void printTestResumes() {
        for (int i = 0 ; i < SIZE; i++) {
            System.out.println("RESUMES[" + i + "] = " + RESUMES[i]);
        }
        System.out.println("RESUME_EXIST = " + RESUME_EXIST);
        System.out.println("RESUME_NEW = " + RESUME_NEW);
        System.out.println();
    }

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
        Resume resumeUpdated = new Resume("Ivanova Elena", UUID_EXIST);
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
    public void getAllSorted() throws Exception {
        List<Resume> resumesList = storage.getAllSorted();
        Resume[] resumes = new Resume[resumesList.size()];
        resumesList.toArray(resumes);
//        Arrays.sort(resumes);
        Arrays.sort(RESUMES, Resume.COMPARATOR);
        Assert.assertArrayEquals("Testing method getAllSorted(): elements are not identical.", RESUMES, resumes);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals("Testing method size(): invalid size value.", SIZE, storage.size());
    }

}
