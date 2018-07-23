package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.storage.AbstractStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;
    List<Resume> resumes = new ArrayList<>();

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
            toWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected Resume toGet(File file) {
        return toRead(file);
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
        if (file.isDirectory() && file.list().length != 0) {
            for (File f : file.listFiles()) {
                toDelete(f);
            }
        }
        file.delete();
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
        readAllFiles(directory);
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

    private void readAllFiles(File file) {
        if (file.isDirectory() && file.list().length != 0) {
            for (File f : file.listFiles()) {
                readAllFiles(f);
            }
        }
        Resume resume = toRead(file);
        resumes.add(resume);
    }

    protected abstract void toWrite(Resume resume, File file) throws IOException;

    protected abstract Resume toRead(File file);
}
