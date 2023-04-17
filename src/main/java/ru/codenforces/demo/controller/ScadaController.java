package ru.codenforces.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codenforces.demo.model.Data;

@RestController()
@RequestMapping(path = "/api")
public class ScadaController {
    @PostMapping("/data_a")
    public ResponseEntity<?> addDigitalDate(@RequestBody Data newEvent) {
        boolean isDateNull = newEvent.getEventDate() == null;
        boolean isEventNull = newEvent.getEvent() == null;
        if (isDateNull) {
            return new ResponseEntity<>(new StatCollectorError(HttpStatus.BAD_REQUEST.value(), "Date value is null(check JSON attribute name)"),HttpStatus.BAD_REQUEST);
        } else if(isEventNull) {
            return new ResponseEntity<>(new StatCollectorError(HttpStatus.BAD_REQUEST.value(), "Event value is null(check JSON attribute name)"),HttpStatus.BAD_REQUEST);
        } else {
            Event event = eventService.save(newEvent);
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        }
    }
    @PostMapping("/data_a")
    public ResponseEntity<?> addAnalogDate(@RequestBody Data newEvent) {
        boolean isDateNull = newEvent.getEventDate() == null;
        boolean isEventNull = newEvent.getEvent() == null;
        if (isDateNull) {
            return new ResponseEntity<>(new StatCollectorError(HttpStatus.BAD_REQUEST.value(), "Date value is null(check JSON attribute name)"),HttpStatus.BAD_REQUEST);
        } else if(isEventNull) {
            return new ResponseEntity<>(new StatCollectorError(HttpStatus.BAD_REQUEST.value(), "Event value is null(check JSON attribute name)"),HttpStatus.BAD_REQUEST);
        } else {
            Event event = eventService.save(newEvent);
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        }
    }
}
