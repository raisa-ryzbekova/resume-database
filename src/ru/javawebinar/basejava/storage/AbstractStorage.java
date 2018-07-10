package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object index = getNotExistedKey(resume.getUuid());
        toSave(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        Object index = getExistedKey(uuid);
        return toGet(index);
    }

    @Override
    public void update(Resume resume) {
        Object index = getExistedKey(resume.getUuid());
        toUpdate(resume, index);
    }

    @Override
    public void delete(String uuid) {
        Object index = getExistedKey(uuid);
        toDelete(index);
    }

    private Object getNotExistedKey(String uuid) {
        Object index = getKey(uuid);
        if (isKey(uuid, index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private Object getExistedKey(String uuid) {
        Object index = getKey(uuid);
        if (!isKey(uuid, index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract void toSave(Resume resume, Object index);

    protected abstract Resume toGet(Object index);

    protected abstract void toUpdate(Resume resume, Object index);

    protected abstract void toDelete(Object index);

    protected abstract Object getKey(String uuid);

    protected abstract boolean isKey(String uuid, Object index);
}
