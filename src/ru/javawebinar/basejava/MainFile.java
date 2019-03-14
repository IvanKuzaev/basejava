package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {

    public static void recursivePrintDirectoryStructure(String tabs, File entry) {
        if (entry.isDirectory()) {
            System.out.println(tabs + "[" + entry.getName() + "]");
            File[] entries = entry.listFiles();
            if (entries != null) {
                for (File e : entries) {
                    recursivePrintDirectoryStructure(tabs + "\t", e);
                }
            }
        } else {
            System.out.println(tabs + entry.getName());
        }
    }

    public static void main(String[] args) {
        File directory = new File(".");
        System.out.println(directory.getAbsolutePath());
        recursivePrintDirectoryStructure("", directory);
    }

}
