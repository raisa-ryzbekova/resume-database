package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    protected final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected void toSave(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected Resume toGet(Object index) {
        return storage.get((int) index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
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
    public Integer getKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
    }

    @Override
    protected boolean isKey(String uuid, Object index) {
        return getKey(uuid) != null && getKey(uuid) != -1;
    }
}
