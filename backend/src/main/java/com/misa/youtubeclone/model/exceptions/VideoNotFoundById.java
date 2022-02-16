package com.misa.youtubeclone.model.exceptions;

public class VideoNotFoundById extends IllegalArgumentException {

    public VideoNotFoundById(String id){
        super(String.format("Video not found by id: %s", id));
    }
}
