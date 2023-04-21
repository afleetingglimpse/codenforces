package ru.codenforces.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codenforces.demo.model.Data;
import ru.codenforces.demo.model.Key;
import ru.codenforces.demo.model.SensorData;
import ru.codenforces.demo.model.Settings;
import ru.codenforces.demo.service.DeviceMsgSender;
import ru.codenforces.demo.service.KeyHandler;
import ru.codenforces.demo.service.SettingsManager;

import java.util.Arrays;
import java.util.logging.Logger;


@RestController
@RequestMapping(path = "/")
public class DeviceController {

    @Autowired
    private DeviceMsgSender deviceMsgSender;

    @Autowired
    private SettingsManager settingsManager;

    @Autowired
    private KeyHandler keyHandler;

    @Value("${sensor.threshold}")
    private int threshold;

    private boolean securityKey;
    private final String securityName = "Security";
    private boolean technicalKey;
    private final String technicalName = "Technical";

    public static final Logger LOGGER = Logger.getLogger(DeviceMsgSender.class.getName());


    @PostMapping("/stop") // later
    public ResponseEntity<?> handleStopRequest() {
        // log("stop")
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            // log("Exception raised")
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/start")
    public ResponseEntity<?> handleStartRequest() {

        try {
            Settings settings = settingsManager.loadSettings();
            if (!settingsManager.settingsSanityCheck(settings)) {
                LOGGER.warning("Error occurred in DeviceController.handleStartRequest");
                LOGGER.warning("Invalid settings");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            LOGGER.info(String.format("Loaded version: %s", settings.getVersion()));
            String key = keyHandler.loadKey();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (IllegalAccessException e) {
            LOGGER.warning("Exception raised in DeviceController.handleStartRequest");
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/signals")
    public void handleSensorRequest(@RequestBody SensorData sensorData) {

        try {
            int sensorValue = sensorData.getValue();
            Data data = new Data();

            if (sensorValue > threshold) {
                LOGGER.warning(String.format("Alarm with level %d", sensorValue));
                data.setStatus(true);
                deviceMsgSender.sendProtectionContactData(data);
            }
            else {
                data.setOperation("send_data");
                data.setValue(sensorValue);
                deviceMsgSender.sendDigitalData(data);
                // LOGGER.info("Data sent to digital port");
                deviceMsgSender.sendAnalogData(data);
                // LOGGER.info("Data sent to analog port");
            }
        }
        // Insert needed exception
        catch (Exception e) {
            // log
        }
    }

    @PostMapping("/key_in")
    public ResponseEntity<?> handleKeyInRequest(@RequestBody Key key) {
        if (key.getName().equals(securityName))
            securityKey = true;

        if (key.getName().equals(technicalName))
            technicalKey = true;

        // log("Key input event: " + str(content['name']))

        if (securityKey && technicalKey) {
            // log("Service input port activated")
            // send request and get response
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/key_out")
    public ResponseEntity<?> handleKeyOutRequest(@RequestBody Key key) {
        if (key.getName().equals(securityName))
            securityKey = false;

        if (key.getName().equals(technicalName))
            technicalKey = false;

        // log("Key input event: " + str(content['name']))

        if (!securityKey && !technicalKey) {
            // log("Service input port deactivated")
            // send request and get response
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
