package com.people;

public class InvalidAccessCodeException extends RuntimeException {
    public InvalidAccessCodeException(String message){
        super(message);
    }
}
