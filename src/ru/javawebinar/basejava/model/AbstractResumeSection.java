package ru.javawebinar.basejava.model;

import java.io.Serializable;

public abstract class AbstractResumeSection implements Serializable {

    static final long serialVersionUID = 124L;

    public abstract Object getData();

}
