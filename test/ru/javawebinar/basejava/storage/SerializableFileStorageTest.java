package ru.javawebinar.basejava.storage;

import java.io.File;

public class SerializableFileStorageTest extends AbstractFileStorageTest {
    public SerializableFileStorageTest() {
        super(new SerializableFileStorage(new File("C:\\Users\\Ivan\\IntelliJ Projects\\basejava\\data")));
    }
}
