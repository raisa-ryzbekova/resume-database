package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
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
    public String getIndexOrKey(String value) {
        return null;
    }

    @Override
    public Object checkIndexIfExistStorageException(String value) {
        Object key = getIndexOrKey(value);
        if (key != null) {
            throw new ExistStorageException(value);
        }
        return key;
    }

    @Override
    public Object checkIndexIfNotExistStorageException(String value) {
        Object key = getIndexOrKey(value);
        if (key == null) {
            throw new NotExistStorageException(value);
        }
        return null;
    }
}
