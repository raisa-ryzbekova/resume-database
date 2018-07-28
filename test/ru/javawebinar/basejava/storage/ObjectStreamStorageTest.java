package ru.javawebinar.basejava.storage;

import org.junit.Test;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR_PATH));
    }
}
