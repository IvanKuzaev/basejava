package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SQLStorageTest extends AbstractStorageTest {

    public SQLStorageTest() {
        super();
        this.storage = new SQLStorage(Config.dbUrl, Config.dbUser, Config.dbPassword);
    }

}
