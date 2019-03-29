package ru.javawebinar.basejava.model;

public enum Sections {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENTS("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    Sections(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
