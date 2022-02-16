package com.misa.youtubeclone.model.exceptions;

public class UserNotFoundBySub extends IllegalArgumentException{
    public UserNotFoundBySub(String sub){
        super(String.format("User not found by sub: %s", sub));
    }
}
