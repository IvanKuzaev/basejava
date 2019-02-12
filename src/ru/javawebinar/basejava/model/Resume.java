package ru.javawebinar.basejava.model;

import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName;

    @Override
    public int compareTo(Resume o) {
        int cName = fullName.compareTo(o.fullName);
        return cName != 0 ? cName : uuid.compareTo(o.uuid);
//        return uuid.compareTo(o.uuid);
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
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
