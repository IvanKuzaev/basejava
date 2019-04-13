package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(Exception e) {
        super(e);
    }

    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + " does not exist", uuid);
    }
}
