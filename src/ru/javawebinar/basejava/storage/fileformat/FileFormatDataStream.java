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

    private void writeStringSection(DataOutputStream dos, StringSection stringSection) throws IOException {
        dos.writeUTF(stringSection.getData());
    }

    private StringSection readStringSection(DataInputStream dis) throws IOException {
        return new StringSection(dis.readUTF());
    }

    private void writeStringListSection(DataOutputStream dos, StringListSection stringListSection) throws IOException {
        writeCollection(dos, stringListSection.getData(), dos::writeUTF);
    }

    private StringListSection readStringListSection(DataInputStream dis) throws IOException {
        return new StringListSection((List<String>)readCollection(dis, dis::readUTF));
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

    private void writeBioSection(DataOutputStream dos, BioSection bioSection) throws IOException {
        writeCollection(dos, bioSection.getData(), ls -> writeLifeStage(dos, ls));
    }

    private BioSection readBioSection(DataInputStream dis) throws IOException {
        List<LifeStage> listLifeStage = (List<LifeStage>)readCollection(dis, () -> readLifeStage(dis));
        return new BioSection(listLifeStage);
    }

    void writeSection(DataOutputStream dos, Resume resume, Sections section) throws IOException {
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                writeStringSection(dos, (StringSection)resume.getSection(section));
                break;
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                writeStringListSection(dos, (StringListSection)resume.getSection(section));
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeBioSection(dos, (BioSection)resume.getSection(section));
                break;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeCollection(dos, resume.getContacts().entrySet(), (e) -> { dos.writeUTF(e.getKey().name()); writeString(dos, e.getValue());});
            writeSection(dos, resume, Sections.OBJECTIVE);
            writeSection(dos, resume, Sections.PERSONAL);
            writeSection(dos, resume, Sections.ACHIEVEMENTS);
            writeSection(dos, resume, Sections.QUALIFICATIONS);
            writeSection(dos, resume, Sections.EXPERIENCE);
            writeSection(dos, resume, Sections.EDUCATION);
//            writeStringSection(dos, (StringSection)resume.getSection(Sections.OBJECTIVE));
//            writeStringSection(dos, (StringSection)resume.getSection(Sections.PERSONAL));
//            writeStringListSection(dos, (StringListSection)resume.getSection(Sections.ACHIEVEMENTS));
//            writeStringListSection(dos, (StringListSection)resume.getSection(Sections.QUALIFICATIONS));
//            writeBioSection(dos, (BioSection)resume.getSection(Sections.EXPERIENCE));
//            writeBioSection(dos, (BioSection)resume.getSection(Sections.EDUCATION));
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContact(Contacts.valueOf(dis.readUTF()), dis.readUTF());
            }
            resume.setSection(Sections.OBJECTIVE, readStringSection(dis));
            resume.setSection(Sections.PERSONAL, readStringSection(dis));
            resume.setSection(Sections.ACHIEVEMENTS, readStringListSection(dis));
            resume.setSection(Sections.QUALIFICATIONS, readStringListSection(dis));
            resume.setSection(Sections.EXPERIENCE, readBioSection(dis));
            resume.setSection(Sections.EDUCATION, readBioSection(dis));
            return resume;
        }
    }

}