package ru.javawebinar.basejava.storage;

        import ru.javawebinar.basejava.storage.fileformat.FileFormatDataStream;

public class DataStreamPathStorageTest extends PathStorageTest {

    public DataStreamPathStorageTest() {
        super(new PathStorage(DIRECTORY, new FileFormatDataStream()));
    }

}
