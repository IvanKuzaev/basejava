package ru.javawebinar.basejava;

public class LazySingleton {
    private volatile static LazySingleton INSTANCE;

    double sin = Math.sin(13.);

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }
}
