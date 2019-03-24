package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.fileformat.FileFormatObjectStream;

public class ObjectStreamPathStorage extends PathStorage {

    public ObjectStreamPathStorage(String directory) {
        super(directory);
        setFileFormatStrategy(new FileFormatObjectStream());
    }

}
