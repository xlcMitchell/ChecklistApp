package com.example.beforeyougo.data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static volatile AppExecutors INSTANCE;
    private final ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

    public static AppExecutors getInstance() {
        if (INSTANCE == null) {
            synchronized (AppExecutors.class) {
                if (INSTANCE == null) INSTANCE = new AppExecutors();
            }
        }
        return INSTANCE;
    }

    public ExecutorService db() {
        return dbExecutor;
    }
}
