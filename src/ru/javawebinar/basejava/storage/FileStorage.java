package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.storage.fileformat.FileFormatStrategy;

import java.io.*;

public class FileStorage extends AbstractDiskStorage<File> {

    public FileStorage(String directory) {
        this(directory, null);
    }

    public FileStorage(String directory, FileFormatStrategy fileFormatStrategy) {
        super(new File(directory), fileFormatStrategy);
    }

    @Override
    protected String getName(File file) {
        return file.getName();
    }

    @Override
    protected boolean isDirectory(File file) {
        return file.isDirectory();
    }

    @Override
    protected boolean isWritableReadable(File file) {
        return file.canWrite() & file.canRead();
    }

    @Override
    protected void doDelete(File file) throws IOException {
        file.delete();
    }

    @Override
    protected void doCreateFile(File file) throws IOException {
        file.createNewFile();
    }

    @Override
    protected File[] toArray() throws IOException {
        return directory.listFiles();
    }

    @Override
    protected OutputStream toOutputStream(File file) {
        try {
            return new FileOutputStream(file);
        } catch(FileNotFoundException e) {
            throw new StorageException("File not found", file.getName());
        }
    }

    @Override
    protected InputStream toInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch(FileNotFoundException e) {
            throw new StorageException("File not found", file.getName());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isResumeExist(File file) {
        return file.exists();
    }

}
