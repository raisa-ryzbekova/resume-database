package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    private static final Resume RESUME_1 = new Resume("name1");
    private static final Resume RESUME_2 = new Resume("name2");
    private static final Resume RESUME_3 = new Resume("Григорий Кислин" + "\n");
    private static final Resume RESUME_4 = new Resume("name4");

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void saveTest() {
        storage.save(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageExceptionTest() {
        storage.save(RESUME_2);
    }

    @Test
    public void getTest() {
        Resume actual = storage.get(RESUME_1.getUuid());
        assertEquals(RESUME_1, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistStorageExceptionTest() {
        storage.get("dummy");
    }

    @Test
    public void getAllSortedTest() {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }

    @Test
    public void updateTest() {
        storage.update(RESUME_1);
        assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageExceptionTest() {
        storage.update(RESUME_4);
    }

    @Test
    public void deleteTest() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistStorageExceptionTest() {
        storage.delete(RESUME_4.getUuid());
    }

    @Test
    public void clearTest() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void sizeTest() {
        assertSize(3);
    }

    private void assertSize(int expected) {
        assertEquals(expected, storage.size());
    }

    @Test
    public void fillDataTest() {
        System.out.println(RESUME_3.getFullName());

        RESUME_3.setContact(ContactType.PHONE, "+7(921) 855-0482");
        RESUME_3.setContact(ContactType.SKYPE, "grigory.kislin");
        RESUME_3.setContact(ContactType.MAIL, "gkislin@yandex.ru");
        RESUME_3.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        RESUME_3.setContact(ContactType.GITHAB, "https://github.com/gkislin");
        RESUME_3.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        RESUME_3.setContact(ContactType.PERSONAL_WEBSITE, "http://gkislin.ru/");

        for (ContactType c : ContactType.values()) {
            System.out.println(c.getTitle() + " " + RESUME_3.getContacts(c));
        }

        String objectiveContent = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
        TextSection personal = new TextSection(objectiveContent);

        String personalContent = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
        TextSection objective = new TextSection(personalContent);

        List<String> achievementContent = new ArrayList<String>() {{
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

        List<String> qualificationContent = new ArrayList<String>() {{
            add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
            add("Version control: Subversion, Git, Mercury, ClearCase, Perforce" +
                    "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
            add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        }};
        ListSection qualification = new ListSection(qualificationContent);

        PositionsInCompany javaOnline_1 = new PositionsInCompany(DateUtil.of(2013, 10), LocalDate.now(),
                "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        List<PositionsInCompany> positionsJavaOnline = new ArrayList<>();
        positionsJavaOnline.add(javaOnline_1);
        Company javaOnline = new Company("Java Online Projects", null, positionsJavaOnline);

        PositionsInCompany wrike_1 = new PositionsInCompany(DateUtil.of(2014, 10), DateUtil.of(2016, 1),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами " +
                "Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, " +
                "авторизация по OAuth1, OAuth2, JWT SSO.");
        List<PositionsInCompany> positionsWrike = new ArrayList<>();
        positionsWrike.add(wrike_1);
        Company wrike = new Company("Wrike", null, positionsWrike);

        PositionsInCompany ritCenter_1 = new PositionsInCompany(DateUtil.of(2012, 4), DateUtil.of(2014, 10),
                "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins)");
        List<PositionsInCompany> positionsRitCenter = new ArrayList<>();
        positionsRitCenter.add(ritCenter_1);
        Company ritCenter = new Company("RIT Center", null, positionsRitCenter);

        ArrayList<Company> workCompanies = new ArrayList<Company>() {{
            add(javaOnline);
            add(wrike);
            add(ritCenter);
        }};
        CompanySection experience = new CompanySection(workCompanies);

        PositionsInCompany coursera_1 = new PositionsInCompany(DateUtil.of(2013, 3), DateUtil.of(2013, 5),
                null, "Functional Programming Principles in Scala\" by Martin Odersky");
        List<PositionsInCompany> positionsCoursera = new ArrayList<>();
        positionsCoursera.add(coursera_1);
        Company coursera = new Company("Coursera", null, positionsCoursera);

        PositionsInCompany luxoft_1 = new PositionsInCompany(DateUtil.of(2011, 3), DateUtil.of(2011, 4),
                null, "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        List<PositionsInCompany> positionsLuxoft = new ArrayList<>();
        positionsLuxoft.add(luxoft_1);
        Company luxoft = new Company("Luxoft", null, positionsLuxoft);

        PositionsInCompany spbu_1 = new PositionsInCompany(DateUtil.of(1993, 9), DateUtil.of(1996, 7),
                null, "Аспирантура (программист С, С++");
        PositionsInCompany spbu_2 = new PositionsInCompany(DateUtil.of(1987, 9), DateUtil.of(1993, 7),
                null, "Инженер (программист Fortran, C)");
        List<PositionsInCompany> positionsSpbu = new ArrayList<>();
        positionsSpbu.add(spbu_1);
        positionsSpbu.add(spbu_2);
        Company spbu = new Company("Санкт-Петербургский национальный исследовательский университет информационных " +
                "технологий, механики и оптики", null, positionsSpbu);

        ArrayList<Company> educationCompanies = new ArrayList<Company>() {{
            add(coursera);
            add(luxoft);
            add(spbu);
        }};
        CompanySection education = new CompanySection(educationCompanies);

        RESUME_3.setSection(SectionType.OBJECTIVE, personal);
        RESUME_3.setSection(SectionType.PERSONAL, objective);
        RESUME_3.setSection(SectionType.ACHIEVEMENT, achievement);
        RESUME_3.setSection(SectionType.QUALIFICATIONS, qualification);
        RESUME_3.setSection(SectionType.EXPERIENCE, experience);
        RESUME_3.setSection(SectionType.EDUCATION, education);

        for (SectionType s : SectionType.values()) {
            System.out.println("\n" + s.getTitle() + " \n" + RESUME_3.getSections(s).toString());
        }
    }
}