package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    // сохранить резюме (вставка нового резюме по индексу (бинарный поиск))
    public void save(Resume r) {
        int indexInsert = Arrays.binarySearch(storage, 0, size, r);
        if(indexInsert < 0) {
            indexInsert = -indexInsert - 1;
        }
        System.arraycopy(storage, indexInsert, storage, indexInsert + 1, size - indexInsert);
        storage[indexInsert] = r;
        size++;
    }

    // удалить резюме, смещение оставшихся объектов по индексу на -1
    public void delete(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        int indexInsert = Arrays.binarySearch(storage, 0, size, searchKey);
        System.arraycopy(storage, indexInsert+1, storage, indexInsert, size-indexInsert);
        size--;
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
