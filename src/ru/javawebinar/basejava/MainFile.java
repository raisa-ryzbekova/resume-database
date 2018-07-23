package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFile {

    private static void showAllFileNames(File file) {
        if (file.isDirectory() && file.list().length != 0) {
            for (File f : file.listFiles()) {
                showAllFileNames(f);
            }
        }
        System.out.println(file.getName());
    }

    public static void main(String[] args) {

        String filePath = ".\\.gitignore";

        File file = new File(filePath);

        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }


        File dir = new File("./src/ru/javawebinar/basejava");
        showAllFileNames(dir);

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
