package ru.codenforces.demo.sensor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@PropertySource("application.properties")
public class Sensor {

    private static final long DELIVERY_INTERVAL_MILLISEC = 500;
    private static final int SIGNAL_RANGE = 20;

    @Value("${sensor.uri}")
    private static String uri;
    public static void main() throws InterruptedException, JsonProcessingException {
        while (true) {
            TimeUnit.SECONDS.sleep(DELIVERY_INTERVAL_MILLISEC);

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
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()) ;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
