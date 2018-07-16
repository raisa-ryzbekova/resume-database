package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapUuidStorage extends AbstractStorage<String> {

    protected final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected void toSave(String uuidKey, Resume resumeValue) {
        storage.put(uuidKey, resumeValue);
    }

    @Override
    protected Resume toGet(String uuidKey) {
        return storage.get(uuidKey);
    }

    @Override
    protected void toUpdate(String uuidKey, Resume resumeValue) {
        storage.put(uuidKey, resumeValue);
    }

    @Override
    protected void toDelete(String uuidKey) {
        storage.remove(uuidKey);
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
        return uuid;
    }

    @Override
    protected boolean isKeyExist(String uuidKey) {
        return storage.containsKey(uuidKey);
    }

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(storage.values());
    }
}
