package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Contacts;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.basejava.ResumeTestData.fillDummyResume;


public abstract class AbstractStorageTest {

    private static final Resume[] RESUMES = {
            new Resume("uuid04", "Ivanov Sergei"),
            new Resume("uuid03", "Petrov Vladimir"),
            new Resume("uuid02", "Petrov Vladimir"),
            new Resume("uuid01", "Sidorova Elena"),
    };
    private static final int SIZE = RESUMES.length;

    private static final Resume RESUME_EXIST = RESUMES[0];
    private static final String UUID_EXIST = RESUME_EXIST.getUuid();

    private static final String UUID_NEW = "uuid_new";
    private static final String FULLNAME_NEW = "John Smith";
    private static final Resume RESUME_NEW = new Resume(UUID_NEW, FULLNAME_NEW);

    protected Storage storage;

    protected AbstractStorageTest() {
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
        for (int i = 0; i < SIZE; i++) {
            fillDummyResume(RESUMES[i], i);
        }
        fillDummyResume(RESUME_NEW, 99999);
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
        Assert.assertEquals("Testing method save(): resumes didn't match.", RESUME_NEW, storage.get(UUID_NEW));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_EXIST);
    }

    @Test
    public void update() throws Exception {
        Resume resumeUpdated = storage.get(UUID_EXIST);
        resumeUpdated.setFullName("Ivanova Elena");
        resumeUpdated.setContact(Contacts.EMAIL, "asd@fgh.jkl");
        resumeUpdated.setContact(Contacts.HOMEPAGE, "www.asdfg.hjkl.com");
        storage.update(resumeUpdated);
        Assert.assertEquals("Testing method update(): resumes didn't match.", resumeUpdated, storage.get(UUID_EXIST));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_NEW);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals("Testing method get(): resumes didn't match.", RESUME_EXIST, storage.get(UUID_EXIST));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_NEW);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        int sizeBefore = storage.size();
        storage.delete(UUID_EXIST);
        Assert.assertEquals("Testing method delete(): size didn't decrement.", sizeBefore - 1, storage.size());
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
        Arrays.sort(RESUMES);
        Assert.assertArrayEquals("Testing method getAllSorted(): elements are not identical.", RESUMES, resumes);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals("Testing method size(): invalid size value.", SIZE, storage.size());
    }

    private void printTestResumes() {
        for (int i = 0 ; i < SIZE; i++) {
            System.out.println("RESUMES[" + i + "] = " + RESUMES[i]);
        }
        System.out.println("RESUME_EXIST = " + RESUME_EXIST);
        System.out.println("RESUME_NEW = " + RESUME_NEW);
        System.out.println();
    }

}
