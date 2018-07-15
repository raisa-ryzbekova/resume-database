package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object key = getNotExistedKey(resume.getUuid());
        toSave(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getExistedKey(uuid);
        return toGet(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAsList();
        Collections.sort(resumes);
        return resumes;
    }

    @Override
    public void update(Resume resume) {
        Object key = getExistedKey(resume.getUuid());
        toUpdate(key, resume);
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

    protected abstract void toSave(Object key, Resume resume);

    protected abstract Resume toGet(Object key);

    protected abstract void toUpdate(Object key, Resume resume);

    protected abstract void toDelete(Object key);

    protected abstract Object getKey(String uuid);

    protected abstract boolean isKeyExist(Object key);

    protected abstract List<Resume> getAsList();
}
