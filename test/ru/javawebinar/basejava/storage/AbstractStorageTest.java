package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.time.Month.*;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    protected static final String STORAGE_DIR_PATH = "C:\\Users\\User\\basejava\\realStorage";

    protected static final File STORAGE_DIR = new File("C:\\Users\\User\\basejava\\realStorage");

    protected final Storage storage;

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume("name1");
        RESUME_2 = new Resume("name2");
        RESUME_3 = new Resume("name3");
        RESUME_4 = new Resume("name4");

        RESUME_1.setContact(ContactType.MAIL, "mail1@ya.ru");
        RESUME_1.setContact(ContactType.PHONE, "11111");

        RESUME_1.setSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        RESUME_1.setSection(SectionType.PERSONAL, new TextSection("Personal Data"));
        RESUME_1.setSection(SectionType.ACHIEVEMENT, new ListSection("Achievement1", "Achievement2", "Achievement3"));
        RESUME_1.setSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        RESUME_1.setSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("Company1", "http://company1.ru",
                                new Company.PositionInCompany(2005, JANUARY, "Pos1", "Func1"),
                                new Company.PositionInCompany(2001, MARCH, 2005, JANUARY, "Pos2", "Func2"))));
        RESUME_1.setSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Institute", null,
                                new Company.PositionInCompany(1996, JANUARY, 2000, DECEMBER, "aspirant", null),
                                new Company.PositionInCompany(2001, MARCH, 2005, JANUARY, "student", null)),
                        new Company("Company2", "http://company2.ru")));

        RESUME_2.setContact(ContactType.SKYPE, "skype2");
        RESUME_2.setContact(ContactType.PHONE, "22222");

        RESUME_2.setSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("Company2", "http://company2.ru",
                                new Company.PositionInCompany(2015, JANUARY, "Pos1", "Func1"))));
    }

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
}