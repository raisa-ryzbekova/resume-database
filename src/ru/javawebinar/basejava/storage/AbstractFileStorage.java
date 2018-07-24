package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
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
        Objects.requireNonNull(directory, "directory mustn't be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " isn't directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " isn't readable/writeable");
        }
        this.directory = directory;
    }

    @Override
    protected void toSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
        toUpdate(file, resume);
    }

    @Override
    protected Resume toGet(File file) {
        try {
            return toRead(file);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected void toUpdate(File file, Resume resume) {
        try {
            toWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected void toDelete(File file) {
        try {
            for (File f : file.listFiles()) {
                toDelete(f);
            }
        } catch (NullPointerException e) {
            throw new StorageException("File mustn't be null", file.getName(), e);
        }
        file.delete();
        if (file.exists()) {
            throw new ExistStorageException(file.getName());
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
        List<Resume> resumes = new ArrayList<>();
        List<File> listNames = readAllFiles(directory);
        for (File file : listNames) {
            try {
                resumes.add(toRead(file));
            } catch (IOException e) {
                throw new StorageException("IO Error", file.getName(), e);
            }
        }
        return resumes;
    }

    @Override
    public int size() {
        return (int) directory.length();
    }

    @Override
    public void clear() {
        directory.delete();
    }

    private List<File> readAllFiles(File file) {
        List<File> names = new ArrayList<>();
        try {
            for (File f : file.listFiles()) {
                names.add(f);
            }
        } catch (NullPointerException e) {
            throw new StorageException("File mustn't be null", file.getName(), e);
        }
        return names;
    }

    protected abstract void toWrite(Resume resume, File file) throws IOException;

    protected abstract Resume toRead(File file) throws IOException;
}
