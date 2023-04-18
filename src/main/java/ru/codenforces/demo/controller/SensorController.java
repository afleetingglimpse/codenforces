package ru.codenforces.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codenforces.demo.model.SensorData;

@RestController
@RequestMapping(path = "/signals")
public class SensorController {

    @PostMapping(value = "/")
    public void handleSensorRequest(@RequestBody SensorData rawData) {
        System.out.println(rawData.getData());
    }
}
