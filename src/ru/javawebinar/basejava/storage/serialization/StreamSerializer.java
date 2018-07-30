package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {

    void toWrite(OutputStream os, Resume resume) throws IOException;

    Resume toRead(InputStream is) throws IOException;
}
