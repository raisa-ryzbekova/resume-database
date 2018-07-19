package ru.javawebinar.basejava.model;

import java.util.List;

public class CompanySection extends Section {

    private final List<Company> sectionContent;

    public CompanySection(List<Company> sectionContent) {
        this.sectionContent = sectionContent;
    }

    @Override
    public String toString() {
        return String.join("\n", sectionContent.toString());
    }
}