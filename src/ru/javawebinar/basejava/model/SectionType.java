package ru.javawebinar.basejava.model;

public enum SectionType {

    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + " :" + value;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);
    }
}