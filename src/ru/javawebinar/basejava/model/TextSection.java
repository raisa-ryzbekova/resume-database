package ru.javawebinar.basejava.model;

public class TextSection extends Section {

    private final String sectionContent;

    public TextSection(String sectionContent) {
        this.sectionContent = sectionContent;
        super.textSection = this;
    }

    @Override
    public String toString() {
        return sectionContent;
    }
}
