package com.misa.youtubeclone.controller;

import com.misa.youtubeclone.dto.UploadVideoResponse;
import com.misa.youtubeclone.dto.VideoDto;
import com.misa.youtubeclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SuppressWarnings("unused")
    public UploadVideoResponse uploadVideo(@RequestParam("file") MultipartFile file) {
        return videoService.uploadVideo(file);
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    @SuppressWarnings("unused")
    public String uploadThumbnail(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId) {
        return videoService.uploadThumbnail(file, videoId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @SuppressWarnings("unused")
    public VideoDto editVideoMetadata(@RequestBody VideoDto dto) {
        return videoService.editVideo(dto);
    }
}
