package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringsSection extends ResumeSection{
    private List<String> strings;

    public StringsSection(String ... strings) {
        this.strings = Arrays.asList(strings);
    }

    @Override
    public List<String> getData() {
        return strings;
    }

    @Override
    public String toString() {
        String string = "";
        for (String e : strings) {
            string += e + "\n";
        }
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringsSection that = (StringsSection) o;
        return Objects.equals(strings, that.strings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strings);
    }
}
