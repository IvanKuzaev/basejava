package ru.javawebinar.basejava.model;

public enum Sections {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
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
