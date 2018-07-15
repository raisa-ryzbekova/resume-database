package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

    static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    protected void toSave(Object index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        implToSave((int) index, resume);
        size++;
    }

    @Override
    protected Resume toGet(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void toUpdate(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected void toDelete(Object index) {
        size--;
        implToDelete((int) index);
        storage[size] = null;
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

    @Override
    protected List<Resume> getAsList() {
        Resume[] resumes = Arrays.copyOfRange(storage, 0, size);
        return Arrays.asList(resumes);
    }

    protected abstract void implToSave(int index, Resume resume);

    protected abstract void implToDelete(int index);
}