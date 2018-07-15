package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    protected final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected void toSave(Object resumeAsKey, Resume resumeAsValue) {
        storage.put(resumeAsValue.getUuid(), resumeAsValue);
    }

    @Override
    protected Resume toGet(Object resumeAsKey) {
        return storage.get(((Resume) resumeAsKey).getUuid());
    }

    @Override
    protected void toUpdate(Object resumeAsKey, Resume resumeAsValue) {
        storage.put(((Resume) resumeAsKey).getUuid(), resumeAsValue);
    }

    @Override
    protected void toDelete(Object resumeAsKey) {
        storage.remove(((Resume) resumeAsKey).getUuid());
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
        return storage.get(uuid);
    }

    @Override
    protected boolean isKeyExist(Object resumeAsKey) {
        return resumeAsKey != null;
    }

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(storage.values());
    }
}