package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {

    protected final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected void toSave(Resume resumeAsKey, Resume resumeAsValue) {
        storage.put(resumeAsValue.getUuid(), resumeAsValue);
    }

    @Override
    protected Resume toGet(Resume resumeAsKey) {
        return (resumeAsKey);
    }

    @Override
    protected void toUpdate(Resume resumeAsKey, Resume resumeAsValue) {
        storage.put(resumeAsKey.getUuid(), resumeAsValue);
    }

    @Override
    protected void toDelete(Resume resumeAsKey) {
        storage.remove(resumeAsKey.getUuid());
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
    protected boolean isKeyExist(Resume resumeAsKey) {
        return resumeAsKey != null;
    }

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(storage.values());
    }
}