package ru.codenforces.demo.security;

import ru.codenforces.demo.Utils;
import ru.codenforces.demo.service.DeviceMsgSender;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.logging.Logger;

public class PBKDF2Hashing {
    private static final String KEY = Utils.getProperty("key.settings.filepath");
    private static final Logger LOGGER = Logger.getLogger(DeviceMsgSender.class.getName());

    private byte[] hash(String key) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        char[] buffer = new char[512];
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        BufferedReader in = new BufferedReader(new FileReader(key));
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        StringBuilder wholeBuffer = null;
        while ((in.read(buffer,0,512)) != -1)
        {
            wholeBuffer.append(buffer);
        }
        KeySpec spec = new PBEKeySpec(wholeBuffer.toString().toCharArray(), salt, 65536, 128);
        LOGGER.warning(Arrays.toString(factory.generateSecret(spec).getEncoded()));
        return factory.generateSecret(spec).getEncoded();
    }


}
