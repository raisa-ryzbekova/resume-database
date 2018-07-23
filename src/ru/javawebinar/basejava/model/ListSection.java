package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {

    private final List<String> sectionContent;

    public ListSection(List<String> sectionContent) {
        Objects.requireNonNull(sectionContent, "(list) section content mustn't be null");
        this.sectionContent = sectionContent;
    }

    public List<String> getSectionContent() {
        return sectionContent;
    }

    @Override
    public String toString() {
        return String.join("\n", sectionContent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(sectionContent, that.sectionContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionContent);
    }
}
