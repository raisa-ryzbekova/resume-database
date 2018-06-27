package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size > STORAGE_LIMIT) {
            System.out.println("The storage overflow.");
        }
        if (index < 0) {
            toSave(resume, index);
            size++;
        } else {
            System.out.println("Resume " + resume.getUuid() + " already exists.");
        }
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.println("\nResume " + resume.getUuid() + " doesn't exist");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            toDelete(index);
            size--;
        } else {
            System.out.println("Resume " + uuid + " doesn't exist");
        }
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Resume " + uuid + " doesn't exist");
        return null;
    }

    /**
     * @return array, contains only Resumes in com.urise.webapp.model.storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected abstract void toSave(Resume resume, int index);

    protected abstract void toDelete(int index);

    protected abstract int getIndex(String uuid);
}
