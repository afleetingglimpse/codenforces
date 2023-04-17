package ru.codenforces.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codenforces.demo.model.CustomError;
import ru.codenforces.demo.model.Data;

@RestController()
@RequestMapping(path = "/signals")
public class ScadaController {
    @PostMapping("/data_b")
    public ResponseEntity<?> addDigitalData(@RequestBody Data dData) {
        try{
            int dataValue = dData.getValue();
//            dData.setOperation("data_d");
//            dData.setStatus(true);
            return new ResponseEntity<>(dData, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Digital Data value is null(check JSON attribute name)"),HttpStatus.BAD_REQUEST);

        }
    }
    @PostMapping("/data_a")
    public ResponseEntity<?> addAnalogData(@RequestBody Data aData) {
        try{
            int dataValue = aData.getValue();
//            aData.setOperation("data_a");
//            aData.setStatus(true);
            return new ResponseEntity<>(aData, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Analog Data value is null(check JSON attribute name)"),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/diagnostic")
    public ResponseEntity<?> addDiagnosticData(@RequestBody Data diagData) {
        try{
            int dataValue = diagData.getValue();
            // с девайса отправляем
//            diagData.setOperation("diagnostic");
//            diagData.setStatus(true);
            return new ResponseEntity<>(diagData, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Diagnostic Data value is null(check JSON attribute name)"),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/key")
    public ResponseEntity<?> addKeyData(@RequestBody Data keyData) {
        try{
            int dataValue = keyData.getValue();
            // с девайса отправляем
//            diagData.setOperation("key");
//            diagData.setStatus(true);
            return new ResponseEntity<>(keyData, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Key Data value is null(check JSON attribute name)"),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/error")
    public ResponseEntity<?> addErrorData(@RequestBody Data errorData) {
        try{
            int dataValue = errorData.getValue();
            // с девайса отправляем
//            diagData.setOperation("error");
//            diagData.setStatus(true);
            return new ResponseEntity<>(errorData, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Error Data value is null(check JSON attribute name)"),HttpStatus.BAD_REQUEST);
        }
    }
}
