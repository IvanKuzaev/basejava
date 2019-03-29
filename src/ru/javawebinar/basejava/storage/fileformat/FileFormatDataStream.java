package ru.javawebinar.basejava.storage.fileformat;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileFormatDataStream implements FileFormatStrategy {

    private void writeStringSection (DataOutputStream dos, StringSection stringSection) throws IOException {
        dos.writeUTF(stringSection.getData());
    }

    private StringSection readStringSection (DataInputStream dis) throws IOException {
        return new StringSection(dis.readUTF());
    }

    private void writeStringListSection (DataOutputStream dos, StringListSection stringListSection) throws IOException {
        dos.writeInt(stringListSection.size());
        for (String string : stringListSection.getData()) {
            dos.writeUTF(string);
        }
    }

    private StringListSection readStringListSection (DataInputStream dis) throws IOException {
        List<String> listString = new ArrayList<String>();
        int stringCount = dis.readInt();
        for (int i = 0; i < stringCount; i++) {
            listString.add(dis.readUTF());
        }
        return new StringListSection(listString);
    }

    private void writeBioSection (DataOutputStream dos, BioSection bioSection) throws IOException {
        dos.writeInt(bioSection.size());
        for (LifeStage lifeStage : bioSection.getData()) {
            Organization organization = lifeStage.getOrganization();
            dos.writeUTF(Objects.requireNonNullElse(organization.getTitle(), ""));
            dos.writeUTF(Objects.requireNonNullElse(organization.getDescription(), ""));
            dos.writeUTF(Objects.requireNonNullElse(organization.getWebLink(), ""));
            dos.writeInt(lifeStage.size());
            for (LifePeriod lifePeriod : lifeStage.getData()) {
                dos.writeInt(lifePeriod.getStartDate().getYear());
                dos.writeInt(lifePeriod.getStartDate().getMonthValue());
                dos.writeInt(lifePeriod.getEndDate().getYear());
                dos.writeInt(lifePeriod.getEndDate().getMonthValue());
                dos.writeUTF(Objects.requireNonNullElse(lifePeriod.getTitle(), ""));
                dos.writeUTF(Objects.requireNonNullElse(lifePeriod.getDescription(), ""));
            }
        }
    }

    private BioSection readBioSection (DataInputStream dis) throws IOException {
        List<LifeStage> listLifeStage = new ArrayList<LifeStage>();
        int lifeStageCount = dis.readInt();
        for (int i = 0 ; i < lifeStageCount; i++) {
            String title = dis.readUTF();
            title = title.length() > 0 ? title : null;
            String description = dis.readUTF();
            description = description.length() > 0 ? description : null;
            String webLink = dis.readUTF();
            webLink = webLink.length() > 0 ? webLink : null;
            Organization organization = new Organization(title, description, webLink);
            List<LifePeriod> listLifePeriod = new ArrayList<LifePeriod>();
            int lifePeriodCount = dis.readInt();
            for(int j = 0; j < lifePeriodCount; j++) {
                LocalDate startDate = LocalDate.of(dis.readInt(), dis.readInt(), 1);
                LocalDate endDate = LocalDate.of(dis.readInt(), dis.readInt(), 1);
                title = dis.readUTF();
                title = title.length() > 0 ? title : null;
                description = dis.readUTF();
                description = description.length() > 0 ? description : null;
                LifePeriod lifePeriod = new LifePeriod(startDate, endDate, title, description);
                listLifePeriod.add(lifePeriod);
            }
            listLifeStage.add(new LifeStage(organization, listLifePeriod));
        }
        return new BioSection(listLifeStage);
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<Contacts, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<Contacts, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            writeStringSection(dos, (StringSection)resume.getSection(Sections.OBJECTIVE));
            writeStringSection(dos, (StringSection)resume.getSection(Sections.PERSONAL));
            writeStringListSection(dos, (StringListSection)resume.getSection(Sections.ACHIEVEMENTS));
            writeStringListSection(dos, (StringListSection)resume.getSection(Sections.QUALIFICATIONS));
            writeBioSection(dos, (BioSection)resume.getSection(Sections.EXPERIENCE));
            writeBioSection(dos, (BioSection)resume.getSection(Sections.EDUCATION));
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