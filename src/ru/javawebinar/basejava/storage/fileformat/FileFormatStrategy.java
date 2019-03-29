package ru.javawebinar.basejava.storage.fileformat;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileFormatStrategy {

    Resume doRead(InputStream is) throws IOException;

    void doWrite(Resume resume, OutputStream os) throws IOException;

}
