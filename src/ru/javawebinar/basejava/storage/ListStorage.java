package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedList;

public class ListStorage extends AbstractStorage {

    protected LinkedList<Resume> storage = new LinkedList<Resume>();

    private int indexIntern;//saves the result of last getIndex() call, invoked in resumeExist() method; non direct value transfer for *Intern methods

    private int getIndex(String uuid) {
        for(Resume resume : storage) {
            if (uuid.equals(resume.getUuid()))
                return storage.indexOf(resume);
        }
        return -1;
    }

    @Override
    protected boolean resumeExist(String uuid) {
        indexIntern = getIndex(uuid);
        return indexIntern >= 0 ? true : false;
    }

    @Override
    protected void clearIntern() {
        storage.clear();
    }

    @Override
    protected void updateIntern(Resume resume) {
        storage.set(indexIntern, resume);
    }

    @Override
    protected void saveIntern(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getIntern(String uuid) {
        return storage.get(indexIntern);
    }

    @Override
    protected void deleteIntern(String uuid) {
        storage.remove(indexIntern);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[1]);
    }

}
