package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    static final int STORAGE_LIMIT = 10000;
    protected int size = 0;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    protected void toSave(Resume resume, Object index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        implToSave(resume, (int) index);
        size++;
    }

    @Override
    protected Resume toGet(Object index) {
        return storage[(int) index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected void toUpdate(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    protected void toDelete(Object index) {
        implToDelete((int) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isKeyExist(Object index) {
        return (int) index >= 0;
    }

    protected abstract void implToSave(Resume resume, int index);

    protected abstract void implToDelete(int index);
}