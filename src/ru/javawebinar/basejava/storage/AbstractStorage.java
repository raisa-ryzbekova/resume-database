package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object index = checkIndexIfExistStorageException(resume.getUuid());
        toSave(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        Object index = checkIndexIfNotExistStorageException(uuid);
        return toGet(index);
    }

    @Override
    public void update(Resume resume) {
        Object index = checkIndexIfNotExistStorageException(resume.getUuid());
        toUpdate(resume, index);
    }

    @Override
    public void delete(String uuid) {
        Object index = checkIndexIfNotExistStorageException(uuid);
        toDelete(index);
    }

    protected abstract void toSave(Resume resume, Object index);

    protected abstract Resume toGet(Object index);

    protected abstract void toUpdate(Resume resume, Object index);

    protected abstract void toDelete(Object index);
}
