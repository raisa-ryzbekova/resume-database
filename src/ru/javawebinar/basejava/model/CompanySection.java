package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class CompanySection extends Section {

    private final List<Company> sectionContent;

    public CompanySection(ArrayList<Company> sectionContent) {
        this.sectionContent = sectionContent;
        super.setSection(this);
    }

    @Override
    public String toString() {
        return String.join("\n", sectionContent.toString());
    }
}