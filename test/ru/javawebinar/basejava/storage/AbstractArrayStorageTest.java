package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    public final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private Resume resume1 = new Resume(UUID_1);
    private Resume resume2 = new Resume(UUID_2);
    private Resume resume3 = new Resume(UUID_3);
    private Resume resume4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws StorageException {
        try {
            for (int i = storage.size() + 1; i < STORAGE_LIMIT + 1; i++) {
                Resume resume = new Resume();
                storage.save(resume);
            }
        } catch (StorageException s) {
            Assert.fail("StorageException error");
        } finally {
            Resume resume = new Resume();
            storage.save(resume);
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveThrowExistStorageException() throws Exception {
        storage.save(resume2);
    }

    @Test
    public void saveTest() throws Exception {
        storage.save(resume4);
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateThrowNotExistStorageException() throws Exception {
        storage.update(resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteThrowNotExistStorageException() {
        storage.delete(UUID_4);
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getThrowNotExistStorageException() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getTest() {
        Resume resume = storage.get(UUID_1);
        Assert.assertTrue(UUID_1.equals(resume.getUuid()));
    }

    @Test
    public void getAllTest() {
        Resume[] resumes = storage.getAll();
        Resume[] resumes1 = new Resume[]{resume1, resume2, resume3};
        Assert.assertArrayEquals(resumes1, resumes);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clearTest() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }
}