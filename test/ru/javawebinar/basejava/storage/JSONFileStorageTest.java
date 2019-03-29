package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.fileformat.FileFormatJSON;

public class JSONFileStorageTest extends FileStorageTest {

    public JSONFileStorageTest() {
        super(new FileStorage(DIRECTORY, new FileFormatJSON()));
    }

}