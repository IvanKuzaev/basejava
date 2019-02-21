package ru.javawebinar.basejava.model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;

    private HashMap<Contacts, DataString> contacts = new HashMap<>();
    private HashMap<Sections, ResumeData<?>> sections = new HashMap<>();

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public DataString getContact(Contacts contact) {
        return contacts.get(contact);
    }

    public void setContact(Contacts contact, DataString data) {
        contacts.put(contact, data);
    }

    public ResumeData<?> getSection(Sections section) {
        return sections.get(section);
    }

    public void setSection(Sections section, ResumeData<?> data) {
        sections.put(section, data);
    }

    @Override
    public int compareTo(Resume o) {
        int cName = fullName.compareTo(o.fullName);
        return cName != 0 ? cName : uuid.compareTo(o.uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        String string = "";
        string += getFullName() + "\n\n";
        for (Contacts c : Contacts.values()) {
            DataString contact = getContact(c);
            if (contact != null) {
                string += contact + "\n";
            }
        }
        string += "\n\n";
        for (Sections s : Sections.values()) {
            ResumeData<?> section = getSection(s);
            string += s.getTitle() + "\n";
            if (section != null) {
                string += section + "\n\n";
            }
        }
        return string;
    }

}
