package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public interface Storage {

    void save(Resume resume);

    void update(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);

    Resume[] getAll();

    int size();

    void clear();

    Object getIndexOrKey(String uuid);

    default Object checkIndexIfExistStorageException(String uuid) {
        Object index = getIndexOrKey(uuid);
        if ((int) index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    default Object checkIndexIfNotExistStorageException(String uuid) {
        Object index = getIndexOrKey(uuid);
        if ((int) index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }
}
