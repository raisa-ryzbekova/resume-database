package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public interface Storage {

    void save(Resume resume);

    void update(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);

    Resume[] getAll();

    int size();

    void clear();
}
