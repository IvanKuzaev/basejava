package ru.javawebinar.basejava.storage.fileformat;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileFormatDataStream implements FileFormatStrategy {

    private interface WriteItem<T>{
        void write(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, WriteItem<T> item) throws IOException {
        dos.writeInt(collection.size());
        for(T t : collection) {
            item.write(t);
        }
    }

    private interface ReadItem<T>{
        T read() throws IOException;
        default Collection<T> collection() {
            return new ArrayList<T>();
        }
    }

    private <T> Collection<T> readCollection(DataInputStream dis, ReadItem<T> item) throws IOException {
        Collection<T> collection = item.collection();
        int itemCount = dis.readInt();
        for (int i = 0; i < itemCount; i++) {
            collection.add(item.read());
        }
        return collection;
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonthValue());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private void writeString(DataOutputStream dos, String string) throws IOException {
        dos.writeUTF(Objects.requireNonNullElse(string, ""));
    }

    private String readString(DataInputStream dis) throws IOException {
        String string = dis.readUTF();
        return string.length() > 0 ? string : null;
    }

    private void writeLifePeriod(DataOutputStream dos, LifePeriod lifePeriod) throws IOException {
        writeLocalDate(dos, lifePeriod.getStartDate());
        writeLocalDate(dos, lifePeriod.getEndDate());
        writeString(dos, lifePeriod.getTitle());
        writeString(dos, lifePeriod.getDescription());
    }

    private LifePeriod readLifePeriod(DataInputStream dis) throws IOException {
        LocalDate startDate = readLocalDate(dis);
        LocalDate endDate = readLocalDate(dis);
        String title = readString(dis);
        String description = readString(dis);
        return new LifePeriod(startDate, endDate, title, description);
    }

    private void writeLifeStage(DataOutputStream dos, LifeStage lifeStage) throws IOException {
        Organization organization = lifeStage.getOrganization();
        writeString(dos, organization.getTitle());
        writeString(dos, organization.getDescription());
        writeString(dos, organization.getWebLink());
        writeCollection(dos, lifeStage.getData(), lp -> writeLifePeriod(dos, lp));
    }

    private LifeStage readLifeStage(DataInputStream dis) throws IOException {
        String title = readString(dis);
        String description = readString(dis);
        String webLink = readString(dis);
        Organization organization = new Organization(title, description, webLink);
        List<LifePeriod> listLifePeriod = (List<LifePeriod>)readCollection(dis, () -> readLifePeriod(dis));
        return new LifeStage(organization, listLifePeriod);
    }

    private void writeSection(DataOutputStream dos, Resume resume, Sections section) throws IOException {
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                dos.writeUTF((String)resume.getSection(section).getData());
                break;
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                writeCollection(dos, (List<String>)resume.getSection(section).getData(), dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeCollection(dos, (List<LifeStage>)resume.getSection(section).getData(), ls -> writeLifeStage(dos, ls));
                break;
        }
    }

    private AbstractResumeSection readSection(DataInputStream dis, Sections section) throws IOException {
        AbstractResumeSection resumeSection = null;
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                resumeSection = new StringSection(dis.readUTF());
                break;
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                resumeSection = new StringListSection((List<String>)readCollection(dis, dis::readUTF));
                break;
            case EXPERIENCE:
            case EDUCATION:
                resumeSection = new BioSection((List<LifeStage>)readCollection(dis, () -> readLifeStage(dis)));
                break;
        }
        return resumeSection;
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeCollection(dos, resume.getContacts().entrySet(), (e) -> { dos.writeUTF(e.getKey().name()); writeString(dos, e.getValue()); });
            writeCollection(dos, resume.getSections().entrySet(), (e) -> { dos.writeUTF(e.getKey().name()); writeSection(dos, resume, e.getKey()); });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactCount = dis.readInt();
            for (int i = 0; i < contactCount; i++) {
                resume.setContact(Contacts.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionCount = dis.readInt();
            for (int i = 0; i < sectionCount; i++) {
                Sections section = Sections.valueOf(dis.readUTF());
                resume.setSection(section, readSection(dis, section));
            }
            return resume;
        }
    }

}