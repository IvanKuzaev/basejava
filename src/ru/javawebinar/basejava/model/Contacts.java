package ru.javawebinar.basejava.model;

public enum Contacts {

    PHONE1("Тел"),
    PHONE2("Тел"),
    PHONE3("Тел"),
    PHONE4("Тел"),
    PHONE5("Тел"),
    EMAIL("Эл.почта"),
    SKYPE("Skype"),
    HOMEPAGE("Сайт"),
    PROFILE1("Профиль в сети"),
    PROFILE2("Профиль в сети"),
    PROFILE3("Профиль в сети"),
    PROFILE4("Профиль в сети"),
    PROFILE5("Профиль в сети");

    private String title;

    Contacts(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
