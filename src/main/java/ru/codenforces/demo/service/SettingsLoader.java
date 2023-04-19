package ru.codenforces.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.codenforces.demo.Utils;
import ru.codenforces.demo.model.Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

@Service
public class SettingsLoader {

    private static final String SETTINGS_FILE_PATH = Utils.getProperty("device.settings.filepath");
    public static final Logger LOGGER = Logger.getLogger(SettingsLoader.class.getName());
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
}
