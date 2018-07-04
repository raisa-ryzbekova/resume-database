package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;

public class ListStorage extends AbstractStorage {

    protected ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size]);
    }

    @Override
    public void clear() {
        storage.clear();
        size = 0;
    }

    @Override
    protected void toSave(Resume resume, int index) {
        index = -index - 1;
        storage.add(index, resume);
    }

    @Override
    protected void toUpdate(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void toDeleteCommonMethod(int index) {
        storage.remove(index);
        storage.trimToSize();
    }

    @Override
    protected Resume toGet(int index) {
        return storage.get(index);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Collections.binarySearch(storage, searchKey);
    }
}
