package ru.codenforces.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codenforces.demo.model.Key;
import ru.codenforces.demo.model.SensorData;


@RestController
@RequestMapping(path = "/")
public class DeviceController {

    @Value("${sensor.threshold}")
    private int threshold;

    @Value("${device.settingsPath}")
    private String settingsPath;

    private boolean securityKey;
    private final String securityName = "Security";
    private boolean technicalKey;
    private final String technicalName = "Technical";


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

    @PostMapping("/start") // later
    public ResponseEntity<?> handleStartRequest() {

        try {

            //

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            // log("Exception raised")
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/signals")
    public void handleSensorRequest(@RequestBody SensorData sensorData) {

        try {
            if (sensorData.getData() > threshold) {
                // send alarm
                // log
            }
            else {
                // send message that all is fine
                // log
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
