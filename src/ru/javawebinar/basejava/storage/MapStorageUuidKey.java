package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapStorageUuidKey extends AbstractStorage {

    protected final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected void toSave(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume toGet(Object key) {
        return storage.get(key);
    }

    @Override
    protected void toUpdate(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
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
    protected String getKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return storage.get(uuid).getUuid();
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
