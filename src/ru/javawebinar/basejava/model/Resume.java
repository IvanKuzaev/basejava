/**
 * Initial resume class
 */

package ru.javawebinar.basejava.model;

import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName;

    public Resume() {
        this("", UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        this("", uuid);
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

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

}
