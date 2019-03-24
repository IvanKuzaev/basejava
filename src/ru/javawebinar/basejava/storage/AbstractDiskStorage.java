package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.fileformat.FileFormatStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public abstract class AbstractDiskStorage<FSE /* extends File & Path */> extends AbstractStorage<FSE> {//FSE - File System Entity

    protected FileFormatStrategy fileFormatStrategy;
    protected FSE directory;
    private int size;

    public AbstractDiskStorage(FSE directory) {
        Objects.requireNonNull(directory, "directory must not be null.");
        this.directory = directory;
        size = 0;
        if (!isDirectory(this.directory)) {
            throw new IllegalArgumentException(getName(this.directory) + " is not directory.");
        }
        if (!isWritableReadable(this.directory)) {
            throw new IllegalArgumentException(getName(this.directory) + " is not readable/writable");
        }
    }

    public void setFileFormatStrategy(FileFormatStrategy ffs) {
        fileFormatStrategy = ffs;
    }

    protected abstract String getName(FSE fse);

    protected abstract boolean isDirectory(FSE fse);

    protected abstract boolean isWritableReadable(FSE fse);

    protected abstract void doDelete(FSE fse) throws IOException;

    protected abstract void doCreateFile(FSE fse) throws IOException;

    protected abstract FSE[] toArray() throws IOException;

    protected abstract OutputStream toOutputStream(FSE fse);

    protected abstract InputStream toInputStream(FSE fse);

    protected void doWrite(Resume resume, FSE fse) throws IOException {
        fileFormatStrategy.doWrite(resume, toOutputStream(fse));
    }

    protected Resume doRead(FSE fse) throws IOException {
        return fileFormatStrategy.doRead(toInputStream(fse));
    }

    @Override
    protected void updateInternal(Resume resume, FSE fse) {
        try {
            doWrite(resume, fse);
        } catch (IOException e) {
            throw new StorageException("File error", getName(fse), e);
        }
    }

    @Override
    protected void saveInternal(Resume resume, FSE fse) {
        try {
            doCreateFile(fse);
            size++;
        } catch (IOException e) {
            throw new StorageException("Couldn't create file", getName(fse), e);
        }
        updateInternal(resume, fse);
    }

    @Override
    protected Resume getInternal(FSE fse) {
        try {
            return doRead(fse);
        } catch (IOException e) {
            throw new StorageException("File error", getName(fse), e);
        }
    }

    @Override
    protected void deleteInternal(FSE fse) {
        try {
            doDelete(fse);
            size--;
        } catch (IOException e) {
            throw new StorageException("File delete error", getName(fse), e);
        }
    }

    @Override
    protected Resume[] getAll() {
        try {
            FSE[] fses = toArray();
            Resume[] resumes = new Resume[fses.length];
            for (int i = 0; i < resumes.length; i++) {
                resumes[i] = getInternal(fses[i]);
            }
            return resumes;
        } catch (IOException e) {
            throw new StorageException("File error", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            for (FSE fse : toArray()) {
                deleteInternal(fse);
            }
        } catch (IOException e) {
            throw new StorageException("File delete error", null);
        }
    }

    @Override
    public int size() {
        return size;
    }

}
