package com.example.config;

import java.nio.file.Path;

/**
 * CHANGE: Updated to use singleton pattern instead of creating new instances.
 * This ensures only one AppSettings instance exists throughout the application.
 */
public class SettingsLoader {
    public AppSettings load(Path file) {

        AppSettings s = AppSettings.getInstance();

        s.loadFromFile(file);
        return s;
    }
}
