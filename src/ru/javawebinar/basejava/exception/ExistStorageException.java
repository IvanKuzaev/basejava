package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(Exception e) {
        super(e);
    }

    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exists", uuid);
    }
}

