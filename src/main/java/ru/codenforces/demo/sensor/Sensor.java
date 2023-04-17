package ru.codenforces.demo.sensor;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sensor {

    //CONTENT_HEADER = {"Content-Type": "application/json"}
    //private final double DELIVERY_INTERVAL_SEC = 0.5;
    private final long DELIVERY_INTERVAL_MILLISEC = 500;
    private final int SIGNAL_RANGE = 20;
    public void startPushing() throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(DELIVERY_INTERVAL_MILLISEC);
            Random random = new Random();
            int signal = random.nextInt(SIGNAL_RANGE);
        }
    }



}
