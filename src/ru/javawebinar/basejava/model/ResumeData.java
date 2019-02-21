package ru.javawebinar.basejava.model;

import java.util.Objects;

public abstract class ResumeData<E> {
    protected String title;
    protected E data;

    public ResumeData(String title, E data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    abstract public void clearData();

    abstract public int size();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumeData<?> that = (ResumeData<?>) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int hashTitle = 0;
        if (title != null) {
            hashTitle = title.hashCode();
        }
        int hashData = 0;
        if (data != null) {
            hashData =  data.hashCode();
        }
        return hashTitle ^ hashData;
    }

    @Override
    public String toString() {
        String titleString = "";
        if (title != null) {
            titleString = title + "\n";
        }
        String dataString = "";
        if (data != null) {
            dataString = "\t" + data + "\n";
        }
        return titleString + dataString;
    }

}
