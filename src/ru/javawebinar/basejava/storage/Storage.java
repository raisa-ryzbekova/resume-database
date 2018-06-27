package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based com.urise.webapp.model.storage for Resumes
 */
public interface Storage {
    void save(Resume resume);

    void update(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);

    /**
     * @return array, contains only Resumes in com.urise.webapp.model.storage (without null)
     */
    Resume[] getAll();

    int size();

    void clear();
}
