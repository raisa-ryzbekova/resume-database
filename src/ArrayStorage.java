import java.util.Arrays;


public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    // обнулить массив
    void clear() {
        for(int i = 0; i < this.size(); i++) {
            storage[i] = null;
        }
    }

    // сохранение резюме (вставка нового резюме за последним ненулевым объектом)
    void save(Resume r) {
        storage[size()] = r;
    }

    // получить резюме по идентификатору (по значению поля)
    Resume get(String uuid) {
        Resume temp = null;
        for (int i = 0; i < this.size(); i++) {
            if (storage[i].uuid == uuid) {
                temp = storage[i];
                break;
            }
        }
        return temp;
    }

    // удаление резюме, смещение оставшихся объектов по индексу на -1
    void delete(String uuid) {
        int i = 0;
        int c = this.size();
        for ( ; i < c; i++) {
            if (storage[i].uuid == uuid) {
                storage[i] = null;
                break;
            } else
                continue;
        }
        for(int j = i; j < c-1; j++){
            String s = storage[j+1].uuid;
            storage[j] = new Resume();
            storage[j].uuid = s;
            storage[j+1] = null;
        }
    }

    // получить копию массива сохраненных резюме (ненулевых объектов)
    Resume[] getAll() {
        return Arrays.copyOf(storage, this.size());
    }

    // получить количество сохраненных резюме (ненулевых объектов)
    int size() {
        int temp = 0;
        for(int i = 0; i < storage.length; i++){
            if(storage[i] != null)
                temp++;
            else
               break;
        }
        return temp;
    }
}
