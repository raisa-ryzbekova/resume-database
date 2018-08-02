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
import java.util.stream.Stream;

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
            throw new StorageException("file create error " + path, getFileName(path), e);
        }
        toUpdate(path, resume);
    }

    @Override
    protected Resume toGet(Path path) {
        try {
            return serializer.toRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("path read error", getFileName(path), e);
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
            throw new StorageException("file delete error", getFileName(path), e);
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
        return getFilesList().map(this::toGet).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::toDelete);
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("directory read error", e);
        }
    }
}
