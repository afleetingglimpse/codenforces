/*
 * Copyright (c) 2023.
 * Kirill Ustimenko
 */

package ru.codenforces.demo.model;

public class Settings {
    private double timeout;
    private int max;
    private int alarmLevel;
    private String output;
    private String version;

    public Settings() {}

    public Settings(double timeout, int max, int alarmLevel, String output) {
        this.timeout = timeout;
        this.max = max;
        this.alarmLevel = alarmLevel;
        this.output = output;
    }

    public double getTimeout() {
        return timeout;
    }

    public void setTimeout(double timeout) {
        this.timeout = timeout;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(int alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "timeout=" + timeout +
                ", max=" + max +
                ", alarmLevel=" + alarmLevel +
                ", output='" + output + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
