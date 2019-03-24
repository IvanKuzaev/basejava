package ru.javawebinar.basejava.model;

import java.util.Objects;

public class StringSection extends AbstractResumeSection {
    static final long serialVersionUID = 129L;
    private String string;

    public StringSection(String string) {
        this.string = string;
    }

    @Override
    public String getData() {
        return string;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringSection that = (StringSection) o;
        return Objects.equals(string, that.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }
}
