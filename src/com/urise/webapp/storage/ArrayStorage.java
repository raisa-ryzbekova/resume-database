package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based com.urise.webapp.model.storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = size();

    // обнулить массив
    public void clear() {
        for(int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    // сохранить резюме (вставка нового резюме за последним ненулевым объектом)
    public void save(Resume r) {
        storage[size] = r;
        size++;
    }

    // получить резюме по идентификатору (по значению поля)
    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid)
                return storage[i];
        }
        return null;
    }

    // удалить резюме, смещение оставшихся объектов по индексу на -1
    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                System.arraycopy(storage, i+1, storage, i, size-(i+1));
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in com.urise.webapp.model.storage (without null)
     */
    // получить копию массива сохраненных резюме (ненулевых объектов)
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    // получить количество сохраненных резюме (ненулевых объектов)
    public int size() {
        return size;
    }
}
