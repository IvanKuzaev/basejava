package ru.javawebinar.basejava.storage.fileformat;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class FileFormatObjectStream implements FileFormatStrategy {

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error load resume", null, e);
        } catch (IOException e) {
            throw new StorageException("File error", null, e);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        } catch (IOException e) {
            throw new StorageException("File error", null, e);
        }
    }

}
