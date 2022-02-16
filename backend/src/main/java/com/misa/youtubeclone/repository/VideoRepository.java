package com.misa.youtubeclone.repository;

import com.misa.youtubeclone.dto.VideoDto;
import com.misa.youtubeclone.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VideoRepository extends MongoRepository<Video, String> {
    List<VideoDto> findByUserId(String userId);
}
