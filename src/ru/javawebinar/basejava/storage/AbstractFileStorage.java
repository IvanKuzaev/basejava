package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null.");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory.");
        }
        if (!directory.canRead() || !directory.canWrite() ) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isResumeExist(File file) {
        return file.exists();
    }

    @Override
    protected void updateInternal(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void saveInternal(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getInternal(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void deleteInternal(File file) {
        file.delete();
    }

    @Override
    protected Resume[] getAll() {
        File[] files = directory.listFiles();
        Resume[] resumes = new Resume[files.length];
        for (int i = 0; i < files.length; i++) {
            try {
                resumes[i] = doRead(files[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resumes;
    }

    @Override
    public void clear() {
        for (File f : directory.listFiles()) {
            boolean result = f.delete();
            System.out.println("Clearing " + f + " : " + result);

        }
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }
}
