package ru.codenforces.demo.sensor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@PropertySource("application.properties")
public class Sensor {

    //CONTENT_HEADER = {"Content-Type": "application/json"}
    //private final double DELIVERY_INTERVAL_SEC = 0.5;
    private final long DELIVERY_INTERVAL_MILLISEC = 500;
    private final int SIGNAL_RANGE = 20;

    @Value("${sensor.url}")
    private String url;
    public void startPushing() throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(DELIVERY_INTERVAL_MILLISEC);
            Random random = new Random();
            int data = random.nextInt(SIGNAL_RANGE);
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.setAccept(acceptableMediaTypes);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<byte[]> result = restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class, data);
            }

            catch (RestClientException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }



}
