package ru.codenforces.demo.service;

import org.springframework.stereotype.Service;
import ru.codenforces.demo.model.Data;

import java.util.logging.Logger;

@Service
public class ProtectionService {
    public static final Logger LOGGER = Logger.getLogger(DeviceMsgSender.class.getName());
    public void react(Data data) {
        LOGGER.warning("Protection system triggered");
        LOGGER.warning(String.format("Alarm went off with signal level %d", data.getValue()));
    }
}
