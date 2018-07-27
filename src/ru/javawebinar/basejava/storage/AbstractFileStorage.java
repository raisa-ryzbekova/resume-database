package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writeable");
        }
        this.directory = directory;
    }

    @Override
    protected void toSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("file create error " + file.getAbsolutePath(), file.getName(), e);
        }
        toUpdate(file, resume);
    }

    @Override
    protected Resume toGet(File file) {
        try {
            return toRead(file);
        } catch (IOException e) {
            throw new StorageException("file read error", file.getName(), e);
        }
    }

    @Override
    protected void toUpdate(File file, Resume resume) {
        try {
            toWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("file write error", file.getName(), e);
        }
    }

    @Override
    protected void toDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("file delete error", file.getName());
        }
    }

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isKeyExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getAsList() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("directory read error", null);
        }
        List<Resume> resumes = new ArrayList<>();
        for (File f : files) {
            resumes.add(toGet(f));
        }
        return resumes;
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list != null) {
            return list.length;
        } else {
            throw new StorageException("directory read error", null);
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                toDelete(f);
            }
        }
    }

    protected abstract void toWrite(File file, Resume resume) throws IOException;

    protected abstract Resume toRead(File file) throws IOException;
}
