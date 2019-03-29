package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.fileformat.FileFormatXML;

public class XMLPathStorageTest extends PathStorageTest {

    public XMLPathStorageTest() {
        super(new PathStorage(DIRECTORY, new FileFormatXML()));
    }

}
