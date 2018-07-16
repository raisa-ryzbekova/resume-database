package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<K> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        K key = getNotExistedKey(resume.getUuid());
        toSave(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        K key = getExistedKey(uuid);
        return toGet(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> resumes = getAsList();
        Collections.sort(resumes);
        return resumes;
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        K key = getExistedKey(resume.getUuid());
        toUpdate(key, resume);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        K key = getExistedKey(uuid);
        toDelete(key);
    }

    private K getNotExistedKey(String uuid) {
        K key = getKey(uuid);
        if (isKeyExist(key)) {
            LOG.warning("Resume with uuid = " + uuid + " already exists");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private K getExistedKey(String uuid) {
        K key = getKey(uuid);
        if (!isKeyExist(key)) {
            LOG.warning("Resume with uuid = " + uuid + " doesn't exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void toSave(K key, Resume resume);

    protected abstract Resume toGet(K key);

    protected abstract void toUpdate(K key, Resume resume);

    protected abstract void toDelete(K key);

    protected abstract K getKey(String uuid);

    protected abstract boolean isKeyExist(K key);

    protected abstract List<Resume> getAsList();
}
