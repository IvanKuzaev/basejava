package ru.javawebinar.basejava.model;

import java.util.Comparator;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    public static final Comparator<Resume> COMPARATOR = (r1, r2) -> {
        int cName = r1.getFullName().compareTo(r2.getFullName());
        if (cName != 0) {
            return cName;
        } else {
            return r1.getUuid().compareTo(r2.getUuid());
        }
    };

    // Unique identifier
    private final String uuid;

    private String fullName;

    public Resume(String fullName) {
        this(fullName, UUID.randomUUID().toString());
    }

    public Resume(String fullName, String uuid) {
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getFullName() {
        return this.fullName;
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

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

}
