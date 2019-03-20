package ru.javawebinar.basejava.storage;

public class ObjectStreamFileStorage extends FileStorage {

    public ObjectStreamFileStorage(String directory) {
        super(directory);
        setStrategy(new FileFormatObjectStream());
    }

}