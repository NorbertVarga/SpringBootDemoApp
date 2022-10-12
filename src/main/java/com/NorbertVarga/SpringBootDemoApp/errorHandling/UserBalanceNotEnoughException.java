package com.NorbertVarga.SpringBootDemoApp.errorHandling;

public class UserBalanceNotEnoughException extends RuntimeException{

    public UserBalanceNotEnoughException(String message) {
        super(message);
    }
}
