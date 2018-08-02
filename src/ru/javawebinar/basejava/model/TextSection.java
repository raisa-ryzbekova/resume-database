package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextSection extends Section {

    private static final long serialVersionUID = 1L;

    private String sectionContent;

    public TextSection() {
    }

    public TextSection(String sectionContent) {
        Objects.requireNonNull(sectionContent, "(text) section content mustn't be null");
        this.sectionContent = sectionContent;
    }

    public String getSectionContent() {
        return sectionContent;
    }

    @Override
    public String toString() {
        return sectionContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(sectionContent, that.sectionContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionContent);
    }
}
