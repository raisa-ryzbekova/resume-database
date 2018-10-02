package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.time.Month.MARCH;

public class TestData {

    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume("name1");
        RESUME_2 = new Resume("name2");
        RESUME_3 = new Resume("name3");
        RESUME_4 = new Resume("name4");

        RESUME_1.setContact(ContactType.MAIL, "mail1@ya.ru");
        RESUME_1.setContact(ContactType.PHONE, "11111");

        RESUME_1.setSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        RESUME_1.setSection(SectionType.ACHIEVEMENT, new ListSection("Achievement1", "Achievement2", "Achievement3"));
        RESUME_1.setSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("Company1", "http://company1.ru",
                                new Company.PositionInCompany(2005, JANUARY, "Pos1", "Func1"),
                                new Company.PositionInCompany(2001, MARCH, 2005, JANUARY, "Pos2", "Func2"))));
        RESUME_1.setSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Institute", "",
                                new Company.PositionInCompany(1996, JANUARY, 2000, DECEMBER, "aspirant", ""),
                                new Company.PositionInCompany(2001, MARCH, 2005, JANUARY, "student", "")),
                        new Company("Company2", "http://company2.ru")));

        RESUME_2.setContact(ContactType.SKYPE, "skype2");
        RESUME_2.setContact(ContactType.PHONE, "22222");
        RESUME_2.setSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("Company2", "http://company2.ru",
                                new Company.PositionInCompany(2015, JANUARY, "Pos1", "Func1"))));
    }
}
