package ru.javawebinar.basejava.storage;

public class ObjectStreamFileStorageTest extends FileStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new ObjectStreamFileStorage("C:\\Users\\Ivan\\IntelliJ Projects\\basejava\\data"));
    }

}