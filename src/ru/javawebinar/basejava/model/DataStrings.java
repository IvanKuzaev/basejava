package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;

public class DataStrings extends DataList<String> {

    public DataStrings(String title, List<String> list) {
        super(title, list);
    }

    public DataStrings(String title, String ... list) {
        super(title, Arrays.asList(list));
    }

}
