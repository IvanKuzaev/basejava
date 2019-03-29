package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.fileformat.FileFormatJSON;

public class JSONPathStorageTest extends PathStorageTest {

    public JSONPathStorageTest() {
        super(new PathStorage(DIRECTORY, new FileFormatJSON()));
    }

}
