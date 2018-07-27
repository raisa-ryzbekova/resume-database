package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {

    private final List<Company> sectionContent;

    public CompanySection(Company... sectionContent) {
        this(Arrays.asList(sectionContent));
    }

    public CompanySection(List<Company> sectionContent) {
        Objects.requireNonNull(sectionContent, "(company) section content mustn't be null");
        this.sectionContent = sectionContent;
    }

    public List<Company> getSectionContent() {
        return sectionContent;
    }

    @Override
    public String toString() {
        return sectionContent.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(sectionContent, that.sectionContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionContent);
    }
}