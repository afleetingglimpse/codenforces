/*
 * Copyright (c) 2023.
 * Egor Adonev, Artyom Kornilov
 */

package ru.codenforces.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codenforces.demo.model.Data;
import ru.codenforces.demo.service.DeviceMsgSender;
import ru.codenforces.demo.service.ProtectionService;
import ru.codenforces.demo.service.SettingsManager;

import java.security.SecureRandom;
import java.lang.Object;

@Component
public class Device {
//	private static String KEY = "12345";
	public Event event = new Event();
	@Autowired
	private DeviceMsgSender deviceMsgSender;
	@Autowired
	private SettingsManager settingsManager;
	public void cron (int t) throws JsonProcessingException, InterruptedException, IllegalAccessException {
		Settings settings = settingsManager.loadSettings();
		settingsManager.settingsSanityCheck(settings);
		while (!event.isSet()){
			Thread thread = Thread.currentThread();
			thread.sleep(t);
			SecureRandom random = new SecureRandom();
			int key = random.nextInt();
			Data secondDat = new Data("send_diagnostic");
			//"Checked with status: "
			secondDat.setStatus(true);
			deviceMsgSender.sendDigitalData(secondDat);
			Data firstDat = new Data("send_key");
			firstDat.setValue(key);
			deviceMsgSender.sendDigitalData(firstDat);
		}
	}
	
}