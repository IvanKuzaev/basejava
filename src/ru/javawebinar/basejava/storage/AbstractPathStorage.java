package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

//TODO: delete later if AbstractDiskStorage and its descendants work well
public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String directory) {
       Objects.requireNonNull(directory, "directory must not be null.");
        this.directory = Paths.get(directory);
        if (!Files.isDirectory(this.directory)) {
            throw new IllegalArgumentException(this.directory.toString() + " is not directory.");
        }
        if (!Files.isReadable(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(this.directory.toString() + " is not readable/writable");
        }
    }

    protected abstract void doWrite(Resume resume, Path path) throws IOException;

    protected abstract Resume doRead(Path path) throws IOException;

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isResumeExist(Path path) {
        return path.getFileName() != null;
    }

    @Override
    protected void updateInternal(Resume resume, Path path) {
        try {
            doWrite(resume, path);
        } catch (IOException e) {
            throw new StorageException("File error", path.toString(), e);
        }
    }

    @Override
    protected void saveInternal(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file", path.toString(), e);
        }
        updateInternal(resume, path);
    }

    @Override
    protected Resume getInternal(Path path) {
        try {
            return doRead(path);
        } catch (IOException e) {
            throw new StorageException("File error", path.toString(), e);
        }
    }

    @Override
    protected void deleteInternal(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toString());
        }
    }

    @Override
    protected Resume[] getAll() {
//        try {
//            return (Resume[])Files.list(directory).map(path -> doRead(path)).toArray();
//        } catch (IOException e) {
//            throw new StorageException("File error", null);
//        }
        return null;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteInternal);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
//        int count = 0;
//        for (Path path : (Path[])Files.list(directory).toArray()) {
//            count++;
//        }
//        return count;
        return 0;
    }

}
