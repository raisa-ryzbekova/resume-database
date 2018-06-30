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
    private Resume resume;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws StorageException {
        try {
            for (int i = storage.size() + 1; i < STORAGE_LIMIT + 1; i++) {
                resume = new Resume();
                storage.save(resume);
            }
        } catch (StorageException s) {
            Assert.fail("StorageException error");
        } finally {
            resume = new Resume();
            storage.save(resume);
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveThrowExistStorageException() throws Exception {
        resume = new Resume(UUID_2);
        storage.save(resume);
    }

    @Test
    public void saveTest() throws Exception {
        resume = new Resume("uuid4");
        storage.save(resume);
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateThrowsNotExistStorageException() throws Exception {
        storage.update(new Resume("uuid4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteThrowsNotExistStorageException() {
        storage.delete("uuid4");
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getThrowsNotExistStorageException() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getTest() {
        resume = storage.get(UUID_1);
        Assert.assertTrue(UUID_1.equals(resume.getUuid()));
    }

    @Test
    public void getAllTest() {
        Resume[] resumes = storage.getAll();
        Resume[] resumes1 = new Resume[3];
        for (int i = 0; i < 3; i++) {
            resumes1[i] = new Resume("uuid" + (i + 1));
        }
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