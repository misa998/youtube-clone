package com.misa.youtubeclone.service;

import com.misa.youtubeclone.dto.UploadVideoResponse;
import com.misa.youtubeclone.dto.VideoDto;
import com.misa.youtubeclone.model.Video;
import com.misa.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public UploadVideoResponse uploadVideo(MultipartFile file) {
        String videoUrl = s3Service.uploadFile(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        var saved = videoRepository.save(video);
        return new UploadVideoResponse(saved.getId(), saved.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto dto) {
        var video = getVideo(dto.getId());

        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setTags(dto.getTags());
        video.setThumbnailUrl(dto.getThumbnailUrl());
        video.setVideoStatus(dto.getVideoStatus());

        videoRepository.save(video);
        return dto;
    }

    private Video getVideo(String id) {
        return videoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cannot find video by id " + id));
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        var video = getVideo(videoId);

        String thumbnailUrl = s3Service.uploadFile(file);
        video.setThumbnailUrl(thumbnailUrl);

        videoRepository.save(video);
        return video.getThumbnailUrl();
    }
}
