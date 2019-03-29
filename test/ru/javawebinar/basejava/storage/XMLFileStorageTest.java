package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.fileformat.FileFormatXML;

public class XMLFileStorageTest extends FileStorageTest {

    public XMLFileStorageTest() {
        super(new FileStorage(DIRECTORY, new FileFormatXML()));
    }

}
