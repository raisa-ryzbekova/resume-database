package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected final List<Resume> storage = new ArrayList<>();

    @Override
    protected void toSave(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected Resume toGet(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void toUpdate(Resume resume, Object index) {
        storage.set((int) index, resume);
    }

    @Override
    protected void toDelete(Object index) {
        storage.remove((int) index);
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
    protected Integer getKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isKeyExist(Object index) {
        return index != null;
    }

    @Override
    protected List<Resume> getAsList() {
        return storage;
    }
}