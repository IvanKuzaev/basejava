package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

//TODO: delete later if AbstractDiskStorage and its descendants work well
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
            throw new StorageException("File error", file.getName(), e);
        }
    }

    @Override
    protected void saveInternal(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file", file.getName(), e);
        }
        updateInternal(resume, file);
    }

    @Override
    protected Resume getInternal(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("File error", file.getName(), e);
        }
    }

    @Override
    protected void deleteInternal(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected Resume[] getAll() {
        File[] files = directory.listFiles();
        if (files != null) {
            Resume[] resumes = new Resume[files.length];
            for (int i = 0; i < files.length; i++) {
                try {
                    resumes[i] = doRead(files[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return resumes;
        } else {
            throw new RuntimeException("File system error");
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                deleteInternal(f);
            }
        } else {
            throw new RuntimeException("File system error");
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        if (files != null) {
            return files.length;
        } else {
            throw new RuntimeException("File system error");
        }
    }
}
