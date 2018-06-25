package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    // получить количество сохраненных резюме (ненулевых объектов)
    public int size() {
        return size;
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

    protected abstract int getIndex(String uuid);
}
