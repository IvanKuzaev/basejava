package ru.javawebinar.basejava.model;

public class DataItem<E> extends ResumeData<E> {

    public DataItem(String title, E item) {
        super(title, item);
    }

    @Override
    public void clearData() {
        data = null;
    }

    @Override
    public int size() {
        if (data != null) {
            return 1;
        } else {
            return 0;
        }
    }

}
