package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object key = getNotExistedKey(resume.getUuid());
        toSave(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getExistedKey(uuid);
        return toGet(key);
    }

    @Override
    public void update(Resume resume) {
        Object key = getExistedKey(resume.getUuid());
        toUpdate(resume, key);
    }

    @Override
    public void delete(String uuid) {
        Object key = getExistedKey(uuid);
        toDelete(key);
    }

    private Object getNotExistedKey(String uuid) {
        Object key = getKey(uuid);
        if (isKeyExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private Object getExistedKey(String uuid) {
        Object key = getKey(uuid);
        if (!isKeyExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void toSave(Resume resume, Object key);

    protected abstract Resume toGet(Object key);

    protected abstract void toUpdate(Resume resume, Object key);

    protected abstract void toDelete(Object key);

    protected abstract Object getKey(String uuid);

    protected abstract boolean isKeyExist(Object key);
}
