package ru.javawebinar.basejava.model;

import java.util.List;

public class ListSection extends Section {

    private final List<String> sectionContent;

    public ListSection(List<String> sectionContent) {
        this.sectionContent = sectionContent;
        super.listSection = this;
    }

    @Override
    public String toString() {
        return String.join("\n", sectionContent);
    }
}
