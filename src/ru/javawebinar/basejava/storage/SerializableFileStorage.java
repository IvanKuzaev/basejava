package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class SerializableFileStorage extends AbstractFileStorage {

    public SerializableFileStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume resume, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(resume);
        oos.flush();
        oos.close();
    }

    @Override
    protected Resume doRead(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        ObjectInputStream ois = new ObjectInputStream(fis);
        Resume resume = null;
        try {
            resume = (Resume)ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resume;
    }

}
