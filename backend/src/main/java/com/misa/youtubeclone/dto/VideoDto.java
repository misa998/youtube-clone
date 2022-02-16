package com.misa.youtubeclone.dto;

import com.misa.youtubeclone.model.Comment;
import com.misa.youtubeclone.model.Video;
import com.misa.youtubeclone.model.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
public class VideoDto {
    private String id;
    private String title;
    private String description;
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private String thumbnailUrl;
    private String userId;
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger dislikes = new AtomicInteger(0);
    private AtomicInteger viewCount = new AtomicInteger(0);
    private List<Comment> commentList;

    public VideoDto(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.tags = video.getTags();
        this.videoUrl = video.getVideoUrl();
        this.videoStatus = video.getVideoStatus();
        this.thumbnailUrl = video.getThumbnailUrl();
        this.likes = new AtomicInteger(video.getLikes());
        this.dislikes = new AtomicInteger(video.getDisLikes());
        this.viewCount = new AtomicInteger(video.getViewCount());
        this.commentList = video.getCommentList();
    }

    public VideoDto(){
        this.tags = new HashSet<>();
        this.videoStatus = VideoStatus.PRIVATE;
        this.likes = new AtomicInteger(0);
        this.dislikes = new AtomicInteger(0);
        this.commentList = new ArrayList<>();
    }

    public void incrementLikes() {
        getLikes().incrementAndGet();
    }

    public void decrementLikes() {
        getLikes().decrementAndGet();
    }

    public void incrementDislikes() {
        getDislikes().incrementAndGet();
    }

    public void decrementDislikes() {
        getDislikes().decrementAndGet();
    }

    public AtomicInteger getLikes(){
        if(this.likes == null){
            this.likes = new AtomicInteger(0);
        }
        return this.likes;
    }

    public AtomicInteger getDislikes(){
        if(this.dislikes == null){
            this.dislikes = new AtomicInteger(0);
        }
        return this.dislikes;
    }

    public Video getEntity() {
        return new Video(id, title, description, userId,
                likes.get(), dislikes.get(), tags, videoUrl, videoStatus,
                viewCount.get(), thumbnailUrl, commentList);
    }

    public void incrementViewCount() {
        viewCount.incrementAndGet();
    }
}
