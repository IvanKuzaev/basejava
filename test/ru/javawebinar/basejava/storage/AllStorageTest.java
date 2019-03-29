package ru.javawebinar.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class,
        MapStorageRemakeTest.class,
        ObjectStreamPathStorageTest.class,
        ObjectStreamFileStorageTest.class,
        XMLFileStorageTest.class,
        XMLPathStorageTest.class,
        JSONFileStorageTest.class,
        JSONPathStorageTest.class,
        DataStreamFileStorageTest.class,
        DataStreamPathStorageTest.class
})
public class AllStorageTest {
}
