package ru.javawebinar.basejava.storage;

public class ObjectStreamPathStorageTest extends PathStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(DIRECTORY));
    }

}
