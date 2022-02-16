package com.misa.youtubeclone.dto;

import com.misa.youtubeclone.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private String sub;
    private Set<String> subscribedToUsers;
    private Set<String> subscribers;
    private Set<String> videoHistory;
    private Set<String> likedVideos;
    private Set<String> dislikedVideos;

    public UserDto() {
        this.subscribedToUsers = ConcurrentHashMap.newKeySet();
        this.subscribers = ConcurrentHashMap.newKeySet();
        this.videoHistory = ConcurrentHashMap.newKeySet();
        this.likedVideos = ConcurrentHashMap.newKeySet();
        this.dislikedVideos = ConcurrentHashMap.newKeySet();
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.fullName = user.getFullName();
        this.emailAddress = user.getEmailAddress();
        this.sub = user.getSub();
        this.subscribedToUsers = user.getSubscribedToUsers();
        this.subscribers = user.getSubscribers();
        this.videoHistory = user.getVideoHistory();
        this.likedVideos = user.getLikedVideos();
        this.dislikedVideos = user.getDisLikedVideos();
    }

    public User getUser() {
        return new User(id, firstName, lastName, fullName, emailAddress, sub, subscribedToUsers, subscribers,
                videoHistory, likedVideos, dislikedVideos);
    }

    public void addToLikedVideos(String videoId) {
        getLikedVideos().add(videoId);
    }

    public void removeLikedVideo(String videoId) {
        getLikedVideos().remove(videoId);
    }

    public void removeDislikedVideo(String videoId) {
        getDislikedVideos().remove(videoId);
    }

    public Set<String> getLikedVideos(){
        if(this.likedVideos == null){
            this.likedVideos = ConcurrentHashMap.newKeySet();
        }
        return this.likedVideos;
    }

    public Set<String> getDislikedVideos(){
        if(this.dislikedVideos == null){
            this.dislikedVideos = ConcurrentHashMap.newKeySet();
        }
        return this.dislikedVideos;
    }

    public void addToDislikedVideos(String videoId) {
        getDislikedVideos().add(videoId);
    }

    public void addToVideoHistory(String id) {
        getVideoHistory().add(id);
    }

    public void addToSubscribedToUsers(String userId) {
        getSubscribedToUsers().add(userId);
    }

    public void addToSubscribers(String userId) {
        getSubscribers().add(userId);
    }

    public void removeFromSubscribedToUsers(String userId) {
        getSubscribedToUsers().remove(userId);
    }

    public void removeFromSubscribers(String userId) {
        getSubscribers().remove(userId);
    }

    public Set<String> getVideoHistory(){
        return Optional.ofNullable(this.videoHistory).orElse(ConcurrentHashMap.newKeySet());
    }

    public Set<String> getSubscribers(){
        return Optional.ofNullable(this.subscribers).orElse(ConcurrentHashMap.newKeySet());
    }

    public Set<String> getSubscribedToUsers(){
        return Optional.ofNullable(this.subscribedToUsers).orElse(ConcurrentHashMap.newKeySet());
    }
}

