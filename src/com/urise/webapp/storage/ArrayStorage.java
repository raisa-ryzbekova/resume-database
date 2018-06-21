package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based com.urise.webapp.model.storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;

    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = size();

    // обнулить массив
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    // обновить резюме
    public void update(Resume r) {
        int index = searchIndex(r.getUuid());
        if (storage[index] != null) {
            storage[index] = r;
        } else {
            System.out.println("Resume " + r.getUuid() + " doesn't exist");
        }
    }

    // сохранить резюме (вставка нового резюме за последним ненулевым объектом)
    public void save(Resume r) {
        int index = searchIndex(r.getUuid());
        if (size < STORAGE_LIMIT) {
            if (index == (-1) && storage[size] == null) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Resume" + r.getUuid() + " already exists.");
            }
        } else {
            System.out.println("The storage overflow.");
        }
    }

    // получить резюме по идентификатору (по значению поля)
    public Resume get (String uuid) {
        int index = searchIndex(uuid);
        if (index != (-1)) {
            return storage[index];
        } else {
            System.out.println("Resume " + uuid + " doesn't exist");
        }
        return null;
    }

    // удалить резюме, смещение оставшихся объектов по индексу на -1
    public void delete(String uuid) {
        int index = searchIndex(uuid);
        if (storage[index] != null) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume " + uuid + " doesn't exist");
        }
    }

    /**
     * @return array, contains only Resumes in com.urise.webapp.model.storage (without null)
     */
    // получить копию массива сохраненных резюме (ненулевых объектов)
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    // получить количество сохраненных резюме (ненулевых объектов)
    public int size() {
        return size;
    }

    // поиск индекса
    public int searchIndex(String uuid){
        for (int i = 0; i < size; i++)
            if (uuid == storage[i].getUuid()){
                return i;
            }
        return -1;
    }
}
