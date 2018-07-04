package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void toUpdate(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void toDeleteCommonMethod(int index) {
        toDeleteFromArrayStorage(index);
        storage[size - 1] = null;
    }

    @Override
    protected Resume toGet(int index) {
        return storage[index];
    }

    protected abstract void toSave(Resume resume, int index);

    protected abstract void toDeleteFromArrayStorage(int index);

    protected abstract int getIndex(String uuid);
}
