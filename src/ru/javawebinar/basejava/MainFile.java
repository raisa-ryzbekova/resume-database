package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFile {

    private static void printAllFileNames(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    System.out.println("\tFile: " + f.getName());
                } else if (f.isDirectory()) {
                    System.out.println("Directory: " + f.getName());
                    printAllFileNames(f);
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
        printAllFileNames(dir);

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}