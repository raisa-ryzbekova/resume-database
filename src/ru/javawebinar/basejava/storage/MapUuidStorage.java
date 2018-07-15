package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapUuidStorage extends AbstractStorage {

    protected final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected void toSave(Object uuidKey, Resume resumeValue) {
        storage.put(resumeValue.getUuid(), resumeValue);
    }

    @Override
    protected Resume toGet(Object uuidKey) {
        return storage.get(uuidKey);
    }

    @Override
    protected void toUpdate(Object uuidKey, Resume resumeValue) {
        storage.put((String) uuidKey, resumeValue);
    }

    @Override
    protected void toDelete(Object uuidKey) {
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
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected boolean isKeyExist(Object uuidKey) {
        return uuidKey != null;
    }

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(storage.values());
    }
}
