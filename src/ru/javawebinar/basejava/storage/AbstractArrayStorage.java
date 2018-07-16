package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    protected void toSave(Integer index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        implToSave(index, resume);
        size++;
    }

    @Override
    protected Resume toGet(Integer index) {
        return storage[index];
    }

    @Override
    protected void toUpdate(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void toDelete(Integer index) {
        size--;
        implToDelete(index);
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
    protected boolean isKeyExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> getAsList() {
        Resume[] resumes = Arrays.copyOfRange(storage, 0, size);
        return Arrays.asList(resumes);
    }

    protected abstract void implToSave(int index, Resume resume);

    protected abstract void implToDelete(int index);
}