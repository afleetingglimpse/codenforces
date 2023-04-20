package ru.codenforces.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codenforces.demo.service.DeviceMsgSender;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/protection_alarm")
public class ProtectionController {
    public static final Logger LOGGER = Logger.getLogger(DeviceMsgSender.class.getName());

    @PostMapping
    public

}
