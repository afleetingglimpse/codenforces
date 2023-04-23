/*
 * Copyright (c) 2023.
 * Kirill Ustimenko, Egor Adonev
 */

package ru.codenforces.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codenforces.demo.model.*;
import ru.codenforces.demo.service.DeviceMsgSender;
import ru.codenforces.demo.service.KeyHandler;
import ru.codenforces.demo.service.SettingsManager;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Runnable;

@RestController
@RequestMapping(path = "/")
public class DeviceController {

    @Autowired
    private DeviceMsgSender deviceMsgSender;

    @Autowired
    private Device device;

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


    @PostMapping("/stop")
    public ResponseEntity<?> handleStopRequest() {
        try {
            LOGGER.log(Level.INFO, "==================== STOPPING ====================");
            device.event.set();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE,"Exception raised");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/start")
    public ResponseEntity<?> handleStartRequest() {

        try {
            //device.event.wait();
            Settings settings = settingsManager.loadSettings();
            if (!settingsManager.settingsSanityCheck(settings)) {
                device.event.set();
                LOGGER.warning("[reloading] stopping all systems");
                handleStopRequest();
                LOGGER.warning("[reloading] new sources was rejected");
                handleStartRequest();
                LOGGER.warning("Error occurred in DeviceController.handleStartRequest");
                LOGGER.warning("Invalid settings");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            LOGGER.info(String.format("Loaded version: %s", settings.getVersion()));
            String key = keyHandler.loadKey();
            int lvl = settings.getAlarmLevel();
            ExecutorService executor = Executors.newFixedThreadPool(3);
            executor.submit(() -> {
                new Task(()-> {
                    try {
                        device.cron((int) (3 * settings.getTimeout() + 1));
                    } catch (JsonProcessingException e) {
                        LOGGER.severe(Arrays.toString(e.getStackTrace()));
                    } catch (InterruptedException e) {
                        LOGGER.severe(Arrays.toString(e.getStackTrace()));
                    } catch (IllegalAccessException e) {
                        LOGGER.severe(Arrays.toString(e.getStackTrace()));
                    }
                });
            });
//            old_hash = md5(NEW_FW_PATHNAME)
//            subprocess.call('cp /storage/new.txt /storage/old.txt', shell=True)
//            new_hash = md5(NEW_FW_PATHNAME)
//            if old_hash != new_hash:
//            print("[rewriting] error in sources found")
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalAccessException e) {
            LOGGER.warning("Exception raised in DeviceController.handleStartRequest");
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //} catch (InterruptedException e) {
        //    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/signals")
    public void handleSensorRequest(@RequestBody SensorData sensorData) {
        try {
            int sensorValue = sensorData.getValue();
            Data data = new Data();
            data.setValue(sensorValue);
            if (sensorValue > threshold) {
                LOGGER.severe(String.format("Alarm with level %d", sensorValue));
                data.setStatus(true);
                deviceMsgSender.sendProtectionContactData(data);
            }
            else {
                data.setOperation("send_data");
                deviceMsgSender.sendDigitalData(data);
                // LOGGER.info("Data sent to digital port");
                deviceMsgSender.sendAnalogData(data);
                // LOGGER.info("Data sent to analog port");
            }
        } catch (JsonProcessingException e) {
                    LOGGER.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    @PostMapping("/key_in")
    public ResponseEntity<?> handleKeyInRequest(@RequestBody Key key) {
        if (key.getName().equals(securityName))
            securityKey = true;
        if (key.getName().equals(technicalName))
            technicalKey = true;

        LOGGER.warning("Key input event: " + key.getName());

        if (securityKey && technicalKey) {
            LOGGER.warning("Service input port activated");
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

        LOGGER.warning("Key output event: " + key.getName());

        if (!securityKey && !technicalKey) {
            LOGGER.warning("Service output port activated");
            // send request and get response
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
