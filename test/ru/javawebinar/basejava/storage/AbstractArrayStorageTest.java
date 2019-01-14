package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {

    private static final String uuid01 = "uuid01", uuid02 = "uuid02", uuid03 = "uuid03", uuid04 = "uuid04", uuid05 = "uuid05";
    private static final String uuidNew = "uuid_new";
    private static final String uuidNotExist = "uuid_not_exist";

    private static final Resume resume1 = new Resume(uuid01);
    private static final Resume resume2 = new Resume(uuid02);
    private static final Resume resume3 = new Resume(uuid03);
    private static final Resume resume4 = new Resume(uuid04);
    private static final Resume resume5 = new Resume(uuid05);
    private static final Resume resumeNew = new Resume(uuidNew);
    private static final Resume resumeNotExist = new Resume(uuidNotExist);


    protected static final Resume[] resumes = { resume1, resume2, resume3, resume4 };
    private static final int size = resumes.length;

    private String uuidExist;
    private Resume resumeExist;
    private int element;

    protected Storage storage = null;

    @Before
    public void setUp() throws Exception {
        storage.clear();
        for (int i = 0; i < size; i++) {
            storage.save(resumes[i]);
        }
        element = (int)Math.random() * size;
        resumeExist = resumes[element];
        uuidExist = resumeExist.getUuid();
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals("Testing method clear(): size equals non zero.", 0, storage.size());
    }

    @Test
    public void save() throws Exception {
        int sizeBefore = storage.size();
        storage.save(resumeNew);
        Assert.assertEquals("Testing method save(): size didn't increment.", sizeBefore + 1, storage.size());
        Assert.assertSame("Testing method save(): invalid Resume reference.", resumeNew, storage.get(uuidNew));
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        try {
            for (int i = size + 1; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("dummy" + i));
            }
        } catch (StorageException e) {
            Assert.fail("Testing method save(): inappropriate storage overflow.");
        }
        storage.save(resumeNew);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resumeExist);
    }

    @Test
    public void update() throws Exception {
        storage.update(resumeExist);
        Assert.assertSame("Testing method update(): invalid Resume reference.", resumeExist, storage.get(uuidExist));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(resumeNotExist);
    }

    @Test
    public void get() throws Exception {
        Assert.assertSame("Testing method get(): invalid Resume reference.", resumeExist, storage.get(uuidExist));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(uuidNotExist);
    }

    @Test
    public void delete() throws Exception {
        int sizeBefore = storage.size();
        storage.delete(uuidExist);
        Assert.assertEquals("Testing method delete(): size didn't decrement.", sizeBefore, storage.size() + 1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(uuidNotExist);
    }

    protected abstract void prepareElements(Resume[] resumes);

    @Test
    public void getAll() throws Exception {
        prepareElements(resumes);
        Assert.assertArrayEquals("Testing method getAll(): elements are not identical.", resumes, storage.getAll());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals("Testing method size(): invalid size value.", size, storage.size());
    }

}