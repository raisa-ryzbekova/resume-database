package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    public Storage storage;

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

    @Test
    public void saveNotThrowsStorageException() throws StorageException {
        try {
            for (int i = 4; i < 100001; i++) {
                Resume resume = new Resume("uuid" + i);
                storage.save(resume);
            }
        } catch (StorageException s) {
            Assert.fail("StorageException error");
        }
    }

    @Test(expected = StorageException.class)
    public void saveThrowsStorageException() throws Exception {
        for (int i = 4; i < 100002; i++) {
            Resume resume = new Resume("uuid" + i);
            storage.save(resume);
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveThrowsExistStorageException() throws Exception {
        Resume resume = new Resume("uuid2");
        storage.save(resume);
    }

    @Test
    public void saveSizeAdd() throws Exception {
        Resume resume = new Resume("uuid4");
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
    public void deleteSizeSubtract() {
        storage.delete("uuid1");
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getThrowsNotExistStorageException() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAllSize() {
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clearSizeNull() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }
}
