package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {

    private final List<String> sectionContent;

    public ListSection(ArrayList<String> sectionContent) {
        this.sectionContent = sectionContent;
        super.setSection(this);
    }

    @Override
    public String toString() {
        return String.join("\n", sectionContent);
    }
}
