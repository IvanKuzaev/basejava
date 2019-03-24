package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.fileformat.FileFormatObjectStream;

public class ObjectStreamFileStorage extends FileStorage {

    public ObjectStreamFileStorage(String directory) {
        super(directory);
        setFileFormatStrategy(new FileFormatObjectStream());
    }

}