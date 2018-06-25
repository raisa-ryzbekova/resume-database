package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    public static int size = 0;

    // получить количество сохраненных резюме (ненулевых объектов)
    public int size() {
        return size;
    }

    // обнулить массив
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    // обновить резюме
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("Resume " + r.getUuid() + " doesn't exist");
        }
    }

    // получить резюме по идентификатору (по значению поля)
    public Resume get (String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("Resume " + uuid + " doesn't exist");
        }
        return null;
    }

    /**
     * @return array, contains only Resumes in com.urise.webapp.model.storage (without null)
     */
    // получить копию массива сохраненных резюме (ненулевых объектов)
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    public abstract void save(Resume r);
    public abstract void delete(String uuid);
}
