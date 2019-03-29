package ru.javawebinar.basejava.storage.fileformat;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileFormatXML implements FileFormatStrategy {
    private XmlParser xmlParser;

    public FileFormatXML() {
        xmlParser = new XmlParser(
                Resume.class,
                AbstractResumeSection.class,
                BioSection.class,
                LifePeriod.class,
                LifeStage.class,
                Organization.class,
                Resume.class,
                StringListSection.class,
                StringSection.class
                );
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.<Resume>unmarshall(reader);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }
}
