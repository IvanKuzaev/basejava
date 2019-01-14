package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    //hardcoded values for testing
    static final private String uuidExist1 = "uuid01", uuidExist2 = "uuid02", uuidExist3 = "uuid03", uuidExist4 = "uuid04";
    static final private String uuidNew = "uuid_new";
    static final private String uuidNotExist = "uuid_not_exist";

    protected Storage storage = null;

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(uuidExist1));
        storage.save(new Resume(uuidExist2));
        storage.save(new Resume(uuidExist3));
        storage.save(new Resume(uuidExist4));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals("Testing method clear(): size equals non zero.", 0, storage.size());
        //additional testing on empty storage
        storage.clear();
        Assert.assertEquals("Testing method clear(): size equals non zero.", 0, storage.size());
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume(uuidNew);
        int sizeBefore = storage.size();
        storage.save(resume);
        int sizeAfter = storage.size();
        Assert.assertEquals("Testing method save(): size didn't increment.", sizeBefore + 1, sizeAfter);
        Resume resumeAfterSave = storage.get(uuidNew);
        Assert.assertEquals("Testing method save(): invalid Resume reference.", resume, resumeAfterSave);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        //make storage overflow
        try {
            long index = 0;
            while (true) {
                storage.save(new Resume("dummy" + index));
            }
        } catch (Exception e) {
        }
        //now let's try to save into the overflood storage
        storage.save(new Resume(uuidNew));//exception must be thrown
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(uuidExist1));//exception must be thrown
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(uuidExist1);
        int sizeBefore = storage.size();
        storage.update(resume);
        int sizeAfter = storage.size();
        Assert.assertEquals("Testing method update(): sizes don't match.", sizeBefore, sizeAfter);
        Resume resumeAfterUpdate = storage.get(uuidExist1);
        Assert.assertEquals("Testing method update(): invalid Resume reference.", resume, resumeAfterUpdate);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume(uuidNotExist));//exception must be thrown
    }

    @Test
    public void get() throws Exception {
        //check for existing element
        int sizeBefore = storage.size();
        Resume resume = storage.get(uuidExist1);
        int sizeAfter = storage.size();
        Assert.assertEquals("Testing method get(): sizes don't match.", sizeBefore, sizeAfter);
        //let's check for new element with reference control
        Resume resumeNew = new Resume(uuidNew);
        storage.save(resumeNew);
        Resume resumeAfterSave = storage.get(uuidNew);
        Assert.assertEquals("Testing method get(): invalid Resume reference.", resumeNew, resumeAfterSave);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(uuidNotExist);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        int sizeBefore = storage.size();
        storage.delete(uuidExist1);
        int sizeAfter = storage.size();
        Assert.assertEquals("Testing method delete(): size didn't decrement.", sizeBefore, sizeAfter+1);
        //let's try to get non existing element
        Resume stubResume = storage.get(uuidExist1);//exception must be thrown
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(uuidNotExist);//exception must be thrown
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes;
        resumes = storage.getAll();
        Assert.assertEquals("Testing method getAll(): sizes don't match.", 4, resumes.length);
        for (Resume resume : resumes) {
            String uuid = resume.getUuid();
            if (!uuid.equals(uuidExist1) &&
                    !uuid.equals(uuidExist2) &&
                    !uuid.equals(uuidExist3) &&
                    !uuid.equals(uuidExist4)) {
                Assert.fail("Testing method getAll(): elements are not identical.");
            }
        }
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals("Testing method size(): invalid size value.", 4, storage.size());
    }
}