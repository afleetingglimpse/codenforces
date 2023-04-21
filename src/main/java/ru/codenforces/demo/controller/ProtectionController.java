/*
 * Copyright (c) 2023.
 * Kirill Ustimenko
 */

package ru.codenforces.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codenforces.demo.model.Data;
import ru.codenforces.demo.service.DeviceMsgSender;
import ru.codenforces.demo.service.ProtectionService;

import java.net.http.HttpResponse;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/protection_alarm")
public class ProtectionController {
    public static final Logger LOGGER = Logger.getLogger(DeviceMsgSender.class.getName());

    @Autowired
    private ProtectionService protectionService;

    @PostMapping
    public ResponseEntity<?> alarm(@RequestBody Data data) {
        protectionService.react(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
