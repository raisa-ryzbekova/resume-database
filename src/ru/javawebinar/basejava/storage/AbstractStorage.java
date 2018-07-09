package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    private Object index;

    @Override
    public void save(Resume resume) {
        index = checkIndexIfExistStorageException(resume.getUuid());
        toSave(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        index = checkIndexIfNotExistStorageException(uuid);
        return toGet(index);
    }

    @Override
    public void update(Resume resume) {
        index = checkIndexIfNotExistStorageException(resume.getUuid());
        toUpdate(resume, index);
    }

    @Override
    public void delete(String uuid) {
        index = checkIndexIfNotExistStorageException(uuid);
        toDelete(index);
    }

    protected abstract void toSave(Resume resume, Object index);

    protected abstract Resume toGet(Object index);

    protected abstract void toUpdate(Resume resume, Object index);

    protected abstract void toDelete(Object index);

    protected abstract Object getIndex(String uuid);

    protected abstract Object checkIndexIfExistStorageException(String uuid);

    protected abstract Object checkIndexIfNotExistStorageException(String uuid);
}
