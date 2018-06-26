package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based com.urise.webapp.model.storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size < STORAGE_LIMIT) {
            if (index == -1) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Resume" + resume.getUuid() + " already exists.");
            }
        } else {
            System.out.println("The storage overflow.");
        }
    }

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

    protected int getIndex(String uuid){
        for (int i = 0; i < size; i++)
            if (uuid.equals(storage[i].getUuid())){
                return i;
            }
        return -1;
    }
}
