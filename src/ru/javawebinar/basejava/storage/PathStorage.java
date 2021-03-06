package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.storage.fileformat.FileFormatStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathStorage extends AbstractDiskStorage<Path> {

    public PathStorage(String directory, FileFormatStrategy fileFormatStrategy) {
        super(Paths.get(directory), fileFormatStrategy);
    }

    public PathStorage(String directory) {
        this(directory, null);
    }

    @Override
    protected String getName(Path path) {
        return path.toFile().getName();
    }

    @Override
    protected boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }

    @Override
    protected boolean isWritableReadable(Path path) {
        return Files.isWritable(path) & Files.isReadable(path);
    }

    @Override
    protected void doDelete(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    protected void doCreateFile(Path path) throws IOException {
        Files.createFile(path);
    }

    @Override
    protected Path[] toArray() throws IOException {
        return Files.list(directory).toArray((n) -> { return new Path[n]; });
    }

    @Override
    protected OutputStream toOutputStream(Path path) {
        try {
            return Files.newOutputStream(path);
        } catch(FileNotFoundException e) {
            throw new StorageException("File not found", getName(path));
        } catch(IOException e) {
            throw new StorageException("File error", getName(path));
        }
    }

    @Override
    protected InputStream toInputStream(Path path) {
        try {
            return Files.newInputStream(path);
        } catch(FileNotFoundException e) {
            throw new StorageException("File not found", getName(path));
        } catch(IOException e) {
            throw new StorageException("File error", getName(path));
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isResumeExist(Path path) {
        return Files.isRegularFile(path);
    }

}
