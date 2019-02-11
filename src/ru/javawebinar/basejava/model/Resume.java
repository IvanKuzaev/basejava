package ru.javawebinar.basejava.model;

import java.util.Comparator;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName;

    public static final Comparator<Resume> COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public int compareTo(Resume o) {
        return COMPARATOR.compare(this, o);
//        return uuid.compareTo(o.uuid);
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) & fullName.equals(resume.getFullName());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode() ^ fullName.hashCode();
    }

    @Override
    public String toString() {
        return "{ " + fullName + ", " + uuid + " }";
    }

}
