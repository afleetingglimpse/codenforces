package ru.codenforces.demo.sensor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.codenforces.demo.Utils;
import ru.codenforces.demo.service.DeviceMsgSender;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.net.URI.create;
public class Sensor {
    private static final long DELIVERY_INTERVAL_MILLISEC = 500;
    private static final int SIGNAL_RANGE = 20;
    // тут кринж
    //"http://localhost:8080/signals";
    public static final Logger LOGGER = Logger.getLogger(DeviceMsgSender.class.getName());
    private static final String URI = Utils.getProperty("sensor.uri");

    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
        while (true) {

            TimeUnit.SECONDS.sleep(DELIVERY_INTERVAL_MILLISEC/1000);

            Random random = new Random();
            int data = random.nextInt(SIGNAL_RANGE);

            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(data);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .setHeader("Content-Type","application/json")
                    .uri(create(URI))
                    .build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                LOGGER.warning("Request sent");
            }
            catch (IOException e) {
                LOGGER.warning(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
