package ru.javawebinar.basejava.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<ContactType, String>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<SectionType, Section>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid mustn't be null");
        Objects.requireNonNull(fullName, "fullName mustn't be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContacts(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void setContact(String contactType, String contact) {
        for (ContactType c : ContactType.values()) {
            if (c.name().equals(contactType)) {
                contacts.put(c, contact);
            }
        }
    }

    public Section getSections(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void setTextSection(String sectionName, TextSection textSection) {
        for (SectionType s : SectionType.values()) {
            if (s.name().equals(sectionName)) {
                sections.put(s, textSection);
            }
        }
    }

    public void setListSection(String sectionName, ListSection listSection) {
        for (SectionType s : SectionType.values()) {
            if (s.name().equals(sectionName)) {
                sections.put(s, listSection);
            }
        }
    }

    public void setCompanySection(String sectionName, CompanySection companySection) {
        for (SectionType s : SectionType.values()) {
            if (s.name().equals(sectionName)) {
                sections.put(s, companySection);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return "Resume [uuid = " + uuid + ", fullName = " + fullName + "]";
    }

    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.fullName);
        return result != 0 ? result : uuid.compareTo(o.uuid);
    }
}