package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFile {

    private static void printAllFileNames(File directory, String indention) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    System.out.println(indention + "Directory: " + f.getName());
                    printAllFileNames(f, indention + "  ");
                }
            }
        }
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
        printAllFileNames(dir, "");

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}