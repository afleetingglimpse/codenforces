package ru.codenforces.demo.model;

public class Data {
    private String operation;
    private boolean status;
    private int value;

    public Data() {}

    public Data(String operation, boolean status, int value) {
        this.operation = operation;
        this.status = status;
        this.value = value;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
