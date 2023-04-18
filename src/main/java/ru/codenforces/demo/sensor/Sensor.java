package ru.codenforces.demo.sensor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class Sensor {

    private static final long DELIVERY_INTERVAL_MILLISEC = 500;
    private static final int SIGNAL_RANGE = 20;

    // тут кринж
    private static final String uri = "http://localhost:8080/signals";

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
                    .uri(URI.create(uri))
                    .build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                // logger here
                System.out.println("Request sent");
            }
            catch (IOException e) {
                System.out.println(Arrays.asList(e.getStackTrace()));//
                // logger here
            }
        }
    }
}
