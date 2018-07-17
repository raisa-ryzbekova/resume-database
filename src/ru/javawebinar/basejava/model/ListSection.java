package ru.javawebinar.basejava.model;

import java.util.ArrayList;

public class ListSection extends Section {

    private final ArrayList<String> sectionContent;

    public ListSection(ArrayList<String> sectionContent) {
        this.sectionContent = sectionContent;
        super.setSection(this);
    }

    @Override
    public String toString() {
        String content = String.join("\n", sectionContent);
        return content;
    }
}
