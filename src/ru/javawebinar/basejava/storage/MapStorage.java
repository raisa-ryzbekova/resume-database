package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.TreeMap;

public class MapStorage extends AbstractStorage {

    protected TreeMap<String, String> storage = new TreeMap<>();

    @Override
    public Resume[] getAll() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    protected void toSave(Resume resume, int index) {

    }

    @Override
    protected void toUpdate(Resume resume, int index) {

    }

    @Override
    protected void toDeleteCommonMethod(int index) {

    }

    @Override
    protected Resume toGet(int index) {
        return null;
    }

    @Override
    protected int getIndex(String uuid) {
        return 0;
    }
}
