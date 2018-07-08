package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        toSaveCommonMethod(resume, checkIndex(resume.getUuid(), "save"));
    }

    @Override
    public Resume get(String uuid) {
        return toGet(checkIndex(uuid, "get"));
    }

    @Override
    public void update(Resume resume) {
        toUpdate(resume, checkIndex(resume.getUuid(), "update"));
    }

    @Override
    public void delete(String uuid) {
        toDeleteCommonMethod(checkIndex(uuid, "delete"));
    }

    private Object checkIndex(String uuid, String functionName) {
        Object index = getIndex(uuid);
        if (functionName.equals("save") && (int) index >= 0) {
            throw new ExistStorageException(uuid);
        } else if ((functionName.equals("update") || functionName.equals("delete") || functionName.equals("get")) && (int) index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract void toSaveCommonMethod(Resume resume, Object index);

    protected abstract Resume toGet(Object index);

    abstract void toUpdate(Resume resume, Object index);

    abstract void toDeleteCommonMethod(Object index);

    protected abstract Object getIndex(String uuid);
}
