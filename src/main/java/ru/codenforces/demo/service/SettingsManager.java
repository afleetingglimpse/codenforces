/*
 * Copyright (c) 2023.
 * Kirill Ustimenko
 */

package ru.codenforces.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.codenforces.demo.Utils;
import ru.codenforces.demo.model.Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@Service
public class SettingsManager {

    private static final String SETTINGS_FILE_PATH = Utils.getProperty("device.settings.filepath");
    public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(SettingsManager.class.getName());
    public Settings loadSettings() {
        Settings settings = new Settings();
        try {
            ObjectMapper mapper = new ObjectMapper();
            settings = mapper.readValue(new FileInputStream(SETTINGS_FILE_PATH), Settings.class);
        }
        catch (IOException e) {
            LOGGER.warning("Failed to load settings file");
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
        return settings;
    }

    // simple sanity check
    public boolean settingsSanityCheck(Settings settings) throws IllegalAccessException {
        if (settings.getTimeout() <= 0)
            return false;
        if (settings.getAlarmLevel() <= 0)
            return false;
        if (settings.getMax() <= 0)
            return false;
        if (settings.getVersion() == null)
            return false;
        if (settings.getOutput() == null)
            return false;
        return true;
    }

}
