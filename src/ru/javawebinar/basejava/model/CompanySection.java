package ru.javawebinar.basejava.model;

import java.util.ArrayList;

public class CompanySection extends Section {

    private final ArrayList<Company> sectionContent;

    public CompanySection(ArrayList<Company> sectionContent) {
        this.sectionContent = sectionContent;
        super.setSection(this);
    }

    @Override
    public String toString() {
        String content = String.join("\n", sectionContent.toString());
        return content;
    }
}