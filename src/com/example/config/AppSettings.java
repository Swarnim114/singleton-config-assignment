package com.example.config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AppSettings implements Serializable {

    private static volatile AppSettings INSTANCE;
    private final Properties props = new Properties();
    private static boolean instantiated = false;
    private boolean loaded = false;

    private AppSettings() {
        synchronized (AppSettings.class) {
            if (instantiated) {
                throw new RuntimeException("Use getInstance() method");
            }
            instantiated = true;

        }
    }

    public static AppSettings getInstance() {
        // lazy init needed
        if (INSTANCE == null) {
            synchronized (AppSettings.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppSettings();
                }
            }
        }
        return INSTANCE;
    }

    public void loadFromFile(Path file) {
        if (loaded) {
            throw new IllegalStateException("Configuration already loaded");
        }
        try (InputStream in = Files.newInputStream(file)) {
            props.load(in);
            loaded = true;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String get(String key) {
        // Add safety check
        if (!loaded) {
            throw new IllegalStateException("Configuration not loaded yet!");
        }
        return props.getProperty(key);
    }

    private Object readResolve() {
        return getInstance();
    }
}