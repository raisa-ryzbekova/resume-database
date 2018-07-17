package ru.javawebinar.basejava.model;

public class Section {

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
