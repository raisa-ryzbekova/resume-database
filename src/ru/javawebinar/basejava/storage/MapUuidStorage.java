package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {

    protected final Map<Resume, Resume> storage = new TreeMap<>();

    @Override
    protected void toSave(Resume resume, Object key) {
        storage.put(resume, resume);
    }

    @Override
    protected Resume toGet(Object key) {
        return storage.get(key);
    }

    @Override
    protected void toUpdate(Resume resume, Object key) {
        storage.put(resume, resume);
    }

    @Override
    protected void toDelete(Object key) {
        storage.remove(key);
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
    protected Resume getKey(String uuid) {
        for (Map.Entry<Resume, Resume> entry : storage.entrySet()) {
            if (entry.getValue().getUuid().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    protected boolean isKeyExist(Object key) {
        return key != null;
    }

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(storage.values());
    }
}
