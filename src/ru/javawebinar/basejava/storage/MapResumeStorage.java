package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    protected final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected void toSave(Resume resume, Object resumeAsKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume toGet(Object key) {
        return storage.get(((Resume) key).getUuid());
    }

    @Override
    protected void toUpdate(Resume resume, Object resumeAsKey) {
        storage.put(((Resume) resumeAsKey).getUuid(), resume);
    }

    @Override
    protected void toDelete(Object key) {
        storage.remove(((Resume) key).getUuid());
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
        return storage.containsKey(uuid) ? storage.get(uuid) : null;
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
