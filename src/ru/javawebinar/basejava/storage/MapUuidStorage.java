package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {

    protected final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected void toSave(Resume resume, Object key) {
        String mapKey = resume.getFullName();
        storage.put(mapKey, resume);
    }

    @Override
    protected Resume toGet(Object key) {
        return storage.get(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void toUpdate(Resume resume, Object key) {
        storage.put((String) key, resume);
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
    public String getKey(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
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
}
