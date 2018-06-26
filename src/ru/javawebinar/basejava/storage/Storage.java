package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based com.urise.webapp.model.storage for Resumes
 */
public interface Storage {
    void clear();

    void update(Resume resume);

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    /**
     * @return array, contains only Resumes in com.urise.webapp.model.storage (without null)
     */
    Resume[] getAll();

    int size();
}
