package com.misa.youtubeclone.model.exceptions;

public class UserRegistrationFailedException extends RuntimeException{

    public UserRegistrationFailedException(String message, Exception e){
        super(message, e);
    }

    public UserRegistrationFailedException(String message){
        super(message);
    }
}
