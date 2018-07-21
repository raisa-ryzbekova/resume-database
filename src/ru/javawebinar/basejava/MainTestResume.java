package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainTestResume {

    public static void main(String[] args) {

        Resume resume = new Resume("Григорий Кислин" + "\n");
        System.out.println(resume.getFullName());

        resume.setContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "grigory.kislin");
        resume.setContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        resume.setContact(ContactType.GITHAB, "https://github.com/gkislin");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        resume.setContact(ContactType.PERSONAL_WEBSITE, "http://gkislin.ru/");

        for (ContactType c : ContactType.values()) {
            System.out.println(c.getTitle() + " " + resume.getContacts(c));
        }

        String objectiveContent = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
        TextSection personal = new TextSection(objectiveContent);

        String personalContent = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
        TextSection objective = new TextSection(personalContent);

        ArrayList<String> achievementContent = new ArrayList<String>() {{
            add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                    "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                    "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                    "Более 1000 выпускников.");
            add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                    "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
            add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, " +
                    "CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO " +
                    "аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        }};
        ListSection achievement = new ListSection(achievementContent);

        ArrayList<String> qualificationContent = new ArrayList<String>() {{
            add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
            add("Version control: Subversion, Git, Mercury, ClearCase, Perforce" +
                    "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
            add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        }};
        ListSection qualification = new ListSection(qualificationContent);

        Company javaOnline = new Company("Java Online Projects", null, LocalDate.of(2013, 10, 1), LocalDate.now(),
                "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Company wrike = new Company("Wrike", null, LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами " +
                "Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, " +
                "авторизация по OAuth1, OAuth2, JWT SSO.");
        Company ritCenter = new Company("RIT Center", null, LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1),
                "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins)");
        ArrayList<Company> workCompanies = new ArrayList<Company>() {{
            add(javaOnline);
            add(wrike);
            add(ritCenter);
        }};
        CompanySection experience = new CompanySection(workCompanies);

        Company coursera = new Company("Coursera", null, LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1),
                null, "Functional Programming Principles in Scala\" by Martin Odersky");
        Company luxoft = new Company("Luxoft", null, LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1),
                null, "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        Company siemens = new Company("Siemens AG", null, LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1),
                null, "3 месяца обучения мобильным IN сетям (Берлин)");
        ArrayList<Company> educationCompanies = new ArrayList<Company>() {{
            add(coursera);
            add(luxoft);
            add(siemens);
        }};
        CompanySection education = new CompanySection(educationCompanies);

        resume.setSection(SectionType.OBJECTIVE, personal);
        resume.setSection(SectionType.PERSONAL, objective);
        resume.setSection(SectionType.ACHIEVEMENT, achievement);
        resume.setSection(SectionType.QUALIFICATIONS, qualification);
        resume.setSection(SectionType.EXPERIENCE, experience);
        resume.setSection(SectionType.EDUCATION, education);

        for (SectionType s : SectionType.values())
            System.out.println("\n" + s.getTitle() + " \n" + resume.getSections(s).toString());
    }
}
