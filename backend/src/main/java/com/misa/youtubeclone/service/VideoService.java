package com.misa.youtubeclone.service;

import com.misa.youtubeclone.dto.CommentDto;
import com.misa.youtubeclone.dto.UploadVideoResponse;
import com.misa.youtubeclone.dto.VideoDto;
import com.misa.youtubeclone.model.Comment;
import com.misa.youtubeclone.model.Video;
import com.misa.youtubeclone.model.exceptions.VideoNotFoundById;
import com.misa.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserService userService;

    public UploadVideoResponse uploadVideo(MultipartFile file) {
        String videoUrl = s3Service.uploadFile(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        var saved = videoRepository.save(video);
        return new UploadVideoResponse(saved.getId(), saved.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto dto) {
        var video = fetchVideoById(dto.getId());

        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setTags(dto.getTags());
        video.setThumbnailUrl(dto.getThumbnailUrl());
        video.setVideoStatus(dto.getVideoStatus());

        videoRepository.save(video);
        return dto;
    }

    private Video fetchVideoById(String id) {
        return videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundById(id));
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        var video = fetchVideoById(videoId);

        String thumbnailUrl = s3Service.uploadFile(file);
        video.setThumbnailUrl(thumbnailUrl);

        videoRepository.save(video);
        return video.getThumbnailUrl();
    }

    public VideoDto getVideoDetails(String videoId) {
        var video = new VideoDto(fetchVideoById(videoId));
        video.incrementViewCount();
        userService.addVideoToHistory(video.getId());
        return new VideoDto(videoRepository.save(video.getEntity()));
    }

    public VideoDto likeVideo(String videoId) {
        VideoDto video = new VideoDto(fetchVideoById(videoId));
        if (userService.isLikedVideo(videoId)) {
            video.decrementLikes();
            userService.removeLikedVideo(videoId);
        } else if (userService.isDislikedVideo(videoId)) {
            video.decrementDislikes();
            userService.removeDislikedVideo(videoId);
            video.incrementLikes();
            userService.addToLikedVideos(videoId);
        } else {
            video.incrementLikes();
            userService.addToLikedVideos(videoId);
        }

        videoRepository.save(video.getEntity());

        return video;
    }

    public VideoDto dislikeVideo(String videoId) {
        VideoDto video = new VideoDto(fetchVideoById(videoId));
        if (userService.isDislikedVideo(videoId)) {
            video.decrementDislikes();
            userService.removeDislikedVideo(videoId);
        } else if (userService.isLikedVideo(videoId)) {
            video.decrementLikes();
            userService.removeLikedVideo(videoId);
            video.incrementDislikes();
            userService.addToDislikedVideo(videoId);
        } else {
            video.incrementDislikes();
            userService.addToDislikedVideo(videoId);
        }

        videoRepository.save(video.getEntity());

        return video;
    }

    public void addComment(String videoId, CommentDto commentDto) {
        var videoDto = new VideoDto(fetchVideoById(videoId));
        videoDto.addComment(mapCommentFromDto(commentDto));
        videoRepository.save(videoDto.getEntity());
    }

    private Comment mapCommentFromDto(CommentDto dto) {
        return new Comment(dto.getId(), dto.getText(), dto.getCreateUserId(), dto.getLikeCount().get(), dto.getDislikeCount().get());
    }

    public List<CommentDto> getAllComments(String videoId) {
        var videoDto = new VideoDto(fetchVideoById(videoId));
        List<Comment> commentList = videoDto.getCommentList();
        return commentList.stream().map(CommentDto::new).collect(Collectors.toList());
    }
}
