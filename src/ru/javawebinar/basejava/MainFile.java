package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {

    public static void recursivePrintDirectoryStructure(int deep, File directory) {
        if (directory.isDirectory()) {
            String tabs = "";
            for (int i = 0; i < deep; i++) {
                tabs += "\t";
            }
            System.out.println(tabs + "[" + directory.getName() + "]");
            File[] entries = directory.listFiles();
            if (entries != null) {
                for (File e : entries) {
                    if (e.isDirectory()) {
                        recursivePrintDirectoryStructure(deep + 1, e);
                    }
                }
                for (File e : entries) {
                    if (!e.isDirectory()) {
                        System.out.println(tabs + "\t" + e.getName());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        File directory = new File(".");
        System.out.println(directory.getAbsolutePath());
        recursivePrintDirectoryStructure(0, directory);
    }

}
