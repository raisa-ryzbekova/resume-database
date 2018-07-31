package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serialization.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private StreamSerializer serializer;

    protected PathStorage(String path, StreamSerializer serializer) {
        Objects.requireNonNull(path, "string path must not be null");
        directory = Paths.get(path);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
        this.serializer = serializer;
    }

    @Override
    protected void toSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("file create error " + path, null, e);
        }
        toUpdate(path, resume);
    }

    @Override
    protected Resume toGet(Path path) {
        try {
            return serializer.toRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("path read error", path.toString(), e);
        }
    }

    @Override
    protected void toUpdate(Path path, Resume resume) {
        try {
            serializer.toWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void toDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("file delete error", path.toString(), e);
        }
    }

    @Override
    protected Path getKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isKeyExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> getAsList() {
        try {
            return Files.list(directory).map(this::toGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("directory read error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("directory read error", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::toDelete);
        } catch (IOException e) {
            throw new StorageException("path delete error", null);
        }
    }
}
