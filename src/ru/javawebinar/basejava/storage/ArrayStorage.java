package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based com.urise.webapp.model.storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void toSave(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    public void toDelete(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++)
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        return -1;
    }
}
