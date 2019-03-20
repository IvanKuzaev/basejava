package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileFormatStrategy {

    Resume doRead(InputStream is);

    void doWrite(Resume resume, OutputStream os);

}
