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

    public void setContacts(String phone, String skype, String mail, String linkedIn, String gitHab, String stackOverflow,
                            String personalWebsite) {
        contacts.put(ContactType.PHONE, phone);
        contacts.put(ContactType.SKYPE, skype);
        contacts.put(ContactType.MAIL, mail);
        contacts.put(ContactType.LINKEDIN, linkedIn);
        contacts.put(ContactType.GITHAB, gitHab);
        contacts.put(ContactType.STACKOVERFLOW, stackOverflow);
        contacts.put(ContactType.PERSONAL_WEBSITE, personalWebsite);
    }

    public Section getSections(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void setSections(TextSection personal, TextSection objective, ListSection achievement, ListSection qualification,
                            CompanySection experience, CompanySection education) {
        sections.put(SectionType.OBJECTIVE, objective);
        sections.put(SectionType.PERSONAL, personal);
        sections.put(SectionType.ACHIEVEMENT, achievement);
        sections.put(SectionType.QUALIFICATIONS, qualification);
        sections.put(SectionType.EXPERIENCE, experience);
        sections.put(SectionType.EDUCATION, education);
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