package com.github.parasmani300.balanceupater.avro;

public class SampleClass {
    String name;
    Integer balance;
    String timestamp;

    public SampleClass() {
    }

    public SampleClass(String name, Integer balance, String timestamp) {
        this.name = name;
        this.balance = balance;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SampleClass{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
