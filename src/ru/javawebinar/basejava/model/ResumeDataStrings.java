package ru.javawebinar.basejava.model;

public class ResumeDataStrings extends ResumeData<String> {
    public ResumeDataStrings(String title, String ... es) throws Throwable {
        super(title, true, es);
    }
}
