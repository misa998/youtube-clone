package com.misa.youtubeclone.dto;

import com.misa.youtubeclone.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {

    private String id;
    private String text;
    private String createUserId;
    private AtomicInteger likeCount;
    private AtomicInteger disLikeCount;

    public CommentDto(){
        this.likeCount = new AtomicInteger(0);
        this.disLikeCount = new AtomicInteger(0);
    }

    public CommentDto(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
        this.createUserId = comment.getCreateUserId();
        this.likeCount = new AtomicInteger(comment.getLikeCount());
        this.disLikeCount = new AtomicInteger(comment.getDisLikeCount());
    }

    public AtomicInteger getLikeCount(){
        return Optional.ofNullable(this.likeCount).orElse(new AtomicInteger(0));
    }

    public AtomicInteger getDislikeCount(){
        return Optional.ofNullable(this.disLikeCount).orElse(new AtomicInteger(0));
    }
}

