package ru.javawebinar.basejava.storage;

public class ObjectStreamPathStorage extends PathStorage {

    public ObjectStreamPathStorage(String directory) {
        super(directory);
        setStrategy(new FileFormatObjectStream());
    }

}
