package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.basejava.TestData.*;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected final Storage storage;

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
        RESUME_1.setContact(ContactType.SKYPE, "NewSkype");
        RESUME_1.setContact(ContactType.PERSONAL_WEBSITE, "java.com");
        RESUME_1.getContacts().remove(ContactType.PHONE, "11111");
        RESUME_1.setSection(SectionType.PERSONAL, new TextSection("Personal Data"));
        RESUME_1.setSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        RESUME_1.getSections().remove(SectionType.OBJECTIVE);
        RESUME_1.setSection(SectionType.OBJECTIVE, new TextSection("Objective2"));
        storage.update(RESUME_1);
        assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageExceptionTest() {
        storage.update(RESUME_4);
    }

    @Test
    public void deleteTest() {
        storage.delete(RESUME_3.getUuid());
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