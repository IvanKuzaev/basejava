package ru.javawebinar.basejava.model;

public class DataString extends DataItem<String> {

    public DataString(String title, String data) {
        super(title, data);
    }

    @Override
    public String toString() {
        String string = "";
        if (title != null) {
            string += title + " ";
        }
        string += data;
        return string;
    }
}
