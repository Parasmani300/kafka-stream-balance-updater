package com.github.parasmani300.balanceupater.avro;

public class NewBalance {
    Integer count;
    Integer balance;
    String timestamp;

    public NewBalance(Integer count, Integer balance, String timestamp) {
        this.count = count;
        this.balance = balance;
        this.timestamp = timestamp;
    }

    public NewBalance() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
}
