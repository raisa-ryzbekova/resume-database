package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    protected final List<Resume> storage = new ArrayList<>();

    @Override
    protected void toSave(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume toGet(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void toUpdate(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void toDelete(Integer index) {
        storage.remove(index.intValue());
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
    protected boolean isKeyExist(Integer index) {
        return index != null;
    }

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(storage);
    }
}