package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;

public class ListStorage extends AbstractStorage {

    protected ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected void toSaveCommonMethod(Resume resume, Object index) {
        index = -(int) index - 1;
        storage.add((int) index, resume);
    }

    @Override
    protected Resume toGet(Object index) {
        return storage.get((int) index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    protected void toUpdate(Resume resume, Object index) {
        storage.set((int) index, resume);
    }

    @Override
    protected void toDeleteCommonMethod(Object index) {
        storage.remove((int) index);
        storage.trimToSize();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Collections.binarySearch(storage, searchKey);
    }
}
