package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File(".\\config\\resumes.properties");

    public static String storageDir;
    public static String dbUrl;
    public static String dbUser;
    public static String dbPassword;

    static {
        try(InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            Config.storageDir = props.getProperty("storage.dir");
            Config.dbUrl = props.getProperty("db.url");
            Config.dbUser = props.getProperty("db.user");
            Config.dbPassword = props.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file" + PROPS.getAbsolutePath());
        }
    }
}
