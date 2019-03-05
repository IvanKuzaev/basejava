package ru.javawebinar.basejava.model;

public enum Contacts {

    MOBILE_PHONE("Моб.тел"),
    EMAIL("Эл.почта"),
    SKYPE("Skype"),
    HOMEPAGE("Сайт"),
    LINKEDIN("Linkedin"),
    GITHUB("Github"),
    STACKOVERFLOW("Stackoverflow");

    private String title;

    Contacts(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
