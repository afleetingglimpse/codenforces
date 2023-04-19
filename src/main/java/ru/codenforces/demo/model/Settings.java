package ru.codenforces.demo.model;

public class Settings {
    private double timeout;
    private int max;
    private int alarm_level;
    private String output;
    private String version;

    public Settings() {}

    public Settings(double timeout, int max, int alarm_level, String output) {
        this.timeout = timeout;
        this.max = max;
        this.alarm_level = alarm_level;
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

    public int getAlarm_level() {
        return alarm_level;
    }

    public void setAlarm_level(int alarm_level) {
        this.alarm_level = alarm_level;
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
                ", alarm_level=" + alarm_level +
                ", output='" + output + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
