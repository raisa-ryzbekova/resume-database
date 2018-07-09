package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void toSaveToArrayStorage(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void toDeleteFromArrayStorage(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}