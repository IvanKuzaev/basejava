package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ResumeData<E> {
    protected String title;
    protected boolean isList;
    protected List<E> elements;

    public ResumeData(String title, boolean isList) {
        this.title = title;
        this.isList = isList;
        this.elements = new ArrayList<E>();
    }

    public ResumeData(String title, boolean isList, E ... elements) {
        this.title = title;
        this.isList = isList;
        this.elements = new ArrayList<E>();
        if (!isList && elements.length > 1) {
            throw new Error("Inproper use of ResumeData constructor");
        }
        for (E e : elements) {
            if (e != null) {
                setElement(e);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public boolean isList() {
        return isList;
    }
    public void clearElements() {
        elements.clear();
    }

    public int size() {
        return elements.size();
    }

    public ListIterator<E> iterator() {
        return elements.listIterator();
    }

    public E getElement() {
        return elements.get(0);
    }

    public E getElement(int i) {
        if (isList) {
            return elements.get(i);
        } else {
            return elements.get(0);
        }
    }

    public void setElement(E e) {
//        if (isList) {
            elements.add(e);
//        } else {
//            elements.set(0, e);
//        }
    }

    public void setElement(int i, E e) {
        if (isList) {
            elements.set(i, e);
        } else {
            elements.set(0, e);
        }
    }

    @Override
    public boolean equals(Object object) {
        ResumeData resumeData = (ResumeData)object;
        boolean a = true;
        a &= title.equals(resumeData.title);
        a &= isList == resumeData.isList;
        a &= elements.equals(resumeData.elements);
        return a;
    }

    @Override
    public int hashCode() {
        int a;
        a = title.hashCode();
        a = 31 * a + (isList? 17 : 37);
        a = 31 * a + elements.hashCode();
        return a;
    }

    @Override
    public String toString() {
        String a = getTitle() + "\n";
        int size = elements.size();
        for (int i = 0; i < size; i++) {
            E e = elements.get(i);
            if (e != null) {
                a += "\t" + e.toString();
                if (i < size - 1) {//no "\n" after the last element
                    a += "\n";
                }
            }
        }
        return a;
    }
}
