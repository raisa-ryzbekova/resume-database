package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based com.urise.webapp.model.storage for Resumes
 */
public interface Storage {
    // обнулить массив
    void clear();

    // обновить резюме
    void update(Resume r);

    // сохранить резюме (вставка нового резюме за последним ненулевым объектом)
    void save(Resume r);

    // получить резюме по идентификатору (по значению поля)
    Resume get(String uuid);

    // удалить резюме, смещение оставшихся объектов по индексу на -1
    void delete(String uuid);

    /**
     * @return array, contains only Resumes in com.urise.webapp.model.storage (without null)
     */
    // получить копию массива сохраненных резюме (ненулевых объектов)
    Resume[] getAll();

    // получить количество сохраненных резюме (ненулевых объектов)
    int size();
}
