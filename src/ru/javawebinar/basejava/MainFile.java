package ru.javawebinar.basejava;

import java.io.File;
import java.util.ArrayList;

public class MainFile {

    public static void recursivePrintDirectoryStructure(String tabs, File directory) {
        if (directory.isDirectory()) {
            System.out.println(tabs + "[" + directory.getName() + "]");
            File[] entries = directory.listFiles();
            if (entries != null) {
                ArrayList<File> dirs = new ArrayList<>();
                ArrayList<File> files = new ArrayList<>();
                for (File e : entries) {
                    if (e.isDirectory()) {
                        dirs.add(e);
                    } else {
                        files.add(e);
                    }
                }
                for (int i = 0; i < dirs.size(); i++) {
                    recursivePrintDirectoryStructure(tabs + "\t", dirs.get(i));
                }
                for (int i = 0; i < files.size(); i++) {
                    System.out.println(tabs + "\t" + files.get(i).getName());
                }
            }
        }
    }

    public static void main(String[] args) {
        File directory = new File(".");
        System.out.println(directory.getAbsolutePath());
        recursivePrintDirectoryStructure("", directory);
    }

}
