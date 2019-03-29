package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.fileformat.FileFormatDataStream;

public class DataStreamFileStorageTest extends FileStorageTest {

    public DataStreamFileStorageTest() {
        super(new FileStorage(DIRECTORY, new FileFormatDataStream()));
    }

}
