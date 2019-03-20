package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringListSection extends AbstractResumeSection {
    private List<String> strings;

    public StringListSection(List<String> strings) {
        Objects.requireNonNull(strings, "String list must not be null");
        this.strings = strings;
    }

    public StringListSection(String ... strings) {
        this(Arrays.asList(strings));
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
        StringListSection that = (StringListSection) o;
        return Objects.equals(strings, that.strings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strings);
    }
}
