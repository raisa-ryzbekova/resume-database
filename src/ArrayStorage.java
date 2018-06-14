import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = size();

    // обнулить массив
    void clear() {
        for(int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    // сохранить резюме (вставка нового резюме за последним ненулевым объектом)
    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    // получить резюме по идентификатору (по значению поля)
    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid)
                return storage[i];
        }
        return null;
    }

    // удалить резюме, смещение оставшихся объектов по индексу на -1
    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                System.arraycopy(storage, i+1, storage, i, size-(i+1));
                break;
            } 
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    // получить копию массива сохраненных резюме (ненулевых объектов)
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    // получить количество сохраненных резюме (ненулевых объектов)
    int size() {
        return size;
    }
}
