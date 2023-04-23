package ru.codenforces.demo.service;

import org.springframework.stereotype.Service;
import ru.codenforces.demo.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

@Service
public class KeyHandler {

    private static final String KEY_FILE_NAME = Utils.getProperty("key.settings.filepath");
    public static final Logger LOGGER = Logger.getLogger(DeviceMsgSender.class.getName());
    public String loadKey() {
        String key = "";
        try (FileReader fr = new FileReader(KEY_FILE_NAME);
            BufferedReader bf = new BufferedReader(fr)) {
            key = bf.readLine();
        }
        catch (IOException e) {
            LOGGER.warning("Failed to load key. Check key file on remote server");
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
        return key;
    }
}
