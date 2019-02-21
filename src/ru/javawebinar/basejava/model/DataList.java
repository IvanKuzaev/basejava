package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;

public class DataList<E> extends ResumeData<List<E>> {

    public DataList(String title, List<E> list) {
        super(title, list);
    }

    public DataList(String title, E ... list) {
        super(title, Arrays.asList(list));
    }

    @Override
    public void clearData() {
        data.clear();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public String toString() {
        String string = "";
        if (title != null) {
            string += title + "\n";
        }
        for (E e : data) {
            string += e + "\n";
        }
        return string;
    }

}
