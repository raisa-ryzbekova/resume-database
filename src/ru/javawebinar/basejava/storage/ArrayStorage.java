package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based com.urise.webapp.model.storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    // обнулить массив
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    // обновить резюме
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("Resume " + r.getUuid() + " doesn't exist");
        }
    }

    // сохранить резюме (вставка нового резюме за последним ненулевым объектом)
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size < STORAGE_LIMIT) {
            if (index == -1) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Resume" + r.getUuid() + " already exists.");
            }
        } else {
            System.out.println("The storage overflow.");
        }
    }

    // удалить резюме, смещение оставшихся объектов по индексу на -1
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
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

    // поиск индекса
    protected int getIndex(String uuid){
        for (int i = 0; i < size; i++)
            if (uuid.equals(storage[i].getUuid())){
                return i;
            }
        return -1;
    }
}
