package ru.javawebinar.basejava.model;

public enum ContactType {

    PHONE("Тел."),
    SKYPE("Skype"),
    MAIL("Почта "),
    LINKEDIN("Профиль LinkedIn"),
    GITHAB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    PERSONAL_WEBSITE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
