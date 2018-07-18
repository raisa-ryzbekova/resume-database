package ru.javawebinar.basejava.model;

public abstract class Section {

    private Section section;

    void setSection(TextSection section) {
        this.section = section;
    }

    void setSection(ListSection section) {
        this.section = section;
    }

    void setSection(CompanySection section) {
        this.section = section;
    }
}
