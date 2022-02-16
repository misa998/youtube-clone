package com.misa.youtubeclone.model.exceptions;

public class UserNotFoundById extends IllegalArgumentException{

    public UserNotFoundById(String id){
        super(String.format("User not found by id: %s", id));
    }
}