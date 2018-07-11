package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.TreeMap;

public class MapUuidStorage extends AbstractStorage {

    protected final TreeMap<String, Resume> storage = new TreeMap<>();

    @Override
    protected void toSave(Resume resume, Object key) {
    }

    @Override
    protected Resume toGet(Object key) {
        return null;
    }

    @Override
    public Resume[] getAll() {
        return null;
    }

    @Override
    protected void toUpdate(Resume resume, Object key) {
    }

    @Override
    protected void toDelete(Object key) {
    }

    @Override
    public void clear() {
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public String getKey(String value) {
        return null;
    }

    @Override
    protected boolean isKeyExist(Object key) {
        return key != null;
    }
}
