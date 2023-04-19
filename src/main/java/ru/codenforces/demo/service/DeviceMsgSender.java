package ru.codenforces.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.codenforces.demo.model.Data;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.logging.Logger;

import static ru.codenforces.demo.Utils.getProperty;
import static java.net.URI.create;
@Service
public class DeviceMsgSender {
    public static final Logger LOGGER = Logger.getLogger(DeviceMsgSender.class.getName());
    private static final String DEVICE_URI = getProperty("device.uri");
    private static final String PROTECTION_URI = getProperty("protection.uri");
    private static final String DIGITAL_PORT_URI = getProperty("main.uri");
    //отправка данных в аналог выходной порт
    public void sendAnalogData(Data data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .setHeader("Content-Type","application/json")
                .uri(create(DEVICE_URI))
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.warning("Request sent by DeviceMsgSender.sendAnalogData");
        }
        catch (IOException | InterruptedException e) {
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
    }
    //отправка контакта тревоги с защитой
    public void sendProtectionContactData(Data data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .setHeader("Content-Type","application/json")
                .uri(create(PROTECTION_URI))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.warning("Request sent by DeviceMsgSender.sendProtectionContactData");
        }
        catch (IOException | InterruptedException e) {
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
    }

    public void sendDigitalData(Data data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);
        HttpClient client = HttpClient.newHttpClient();
        String digitalEndPoint = null;
        if (data.getOperation().equals("send_data")){
            digitalEndPoint = "data_d";
        } else if (data.getOperation().equals("send_diagnostic")) {
            digitalEndPoint = "diagnostic";
        } else if (data.getOperation().equals("send_key")) {
            digitalEndPoint = "key";
        } else if (data.getOperation().equals("send_error")) {
            digitalEndPoint = "error";
        }
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .setHeader("Content-Type","application/json")
                .uri(create(DIGITAL_PORT_URI+digitalEndPoint))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.warning("Request sent by DeviceMsgSender.sendDigitalData");
        }
        catch (IOException | InterruptedException e) {
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
    }

}
