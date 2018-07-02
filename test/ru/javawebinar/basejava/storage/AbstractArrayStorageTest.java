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
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void saveTest() {
        storage.save(resume4);
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void saveOverflowTest() {
        try {
            for (int i = storage.size(); i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException s) {
            Assert.fail("StorageException error");
        }
        storage.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageExceptionTest() {
        storage.save(resume2);
    }

    @Test
    public void updateTest() {
        storage.update(resume1);
        Assert.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageExceptionTest() {
        storage.update(resume4);
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistStorageExceptionTest() {
        storage.delete(UUID_4);
    }

    @Test
    public void getTest() {
        Resume actual = storage.get(UUID_1);
        Assert.assertEquals(resume1, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistStorageExceptionTest() {
        storage.get("dummy");
    }

    @Test
    public void getAllTest() {
        Resume[] expect = storage.getAll();
        Resume[] actual = {resume1, resume2, resume3};
        Assert.assertArrayEquals(expect, actual);
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clearTest() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }
}