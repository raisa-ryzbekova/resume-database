package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based com.urise.webapp.model.storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;

    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = size();

    Resume resume;
    Resume[] tempStorage;

    // обнулить массив
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    // обновить резюме
    public void update(Resume r) {
        resume = get(r.getUuid());
        resume = r;
    }

    // сохранить резюме (вставка нового резюме за последним ненулевым объектом)
    public void save(Resume resume) {
        if (size < STORAGE_LIMIT) {
            if (storage[size] == null) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Resume" + resume.getUuid() + " already exists.");
            }
        } else {
            System.out.println("The storage overflow.");
        }
    }

    // получить резюме по идентификатору (по значению поля)
    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                if (storage[i] != null)
                    return storage[i];
                else
                    System.out.println("Resume " + uuid + " doesn't exist");
                break;
            }
        }
        return null;
    }

    // удалить резюме, смещение оставшихся объектов по индексу на -1
    public void delete(String uuid) {
        resume = get(uuid);
        tempStorage = getAll();
        int index = Arrays.asList(tempStorage).indexOf(resume)+1;
        System.arraycopy(storage, index+1, storage, index, size-(index+1));
        size--;
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
}
