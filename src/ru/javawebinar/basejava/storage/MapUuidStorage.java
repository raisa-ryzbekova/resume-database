package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.TreeMap;

public class MapUuidStorage extends AbstractStorage {

    protected TreeMap<String, Resume> storage = new TreeMap<>();

    @Override
    protected void toSaveCommonMethod(Resume resume, Object index) {
    }

    @Override
    protected Resume toGet(Object index) {
        return null;
    }

    @Override
    public Resume[] getAll() {
        return null;
    }

    @Override
    protected void toUpdate(Resume resume, Object index) {
    }

    @Override
    protected void toDeleteCommonMethod(Object index) {
    }

    @Override
    public void clear() {
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getIndex(String key) {
        return null;
    }
}
