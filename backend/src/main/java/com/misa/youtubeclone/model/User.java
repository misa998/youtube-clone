package com.misa.youtubeclone.model;

import com.misa.youtubeclone.dto.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "User")
@Data
@AllArgsConstructor
public class User {

    @Id
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
    private Set<String> disLikedVideos;

    public User(UserInfoDTO userInfoDTO){
        this.id = userInfoDTO.getId();
        this.firstName = userInfoDTO.getGivenName();
        this.lastName = userInfoDTO.getFamilyName();
        this.fullName = userInfoDTO.getName();
        this.emailAddress = userInfoDTO.getEmail();
        this.sub = userInfoDTO.getSub();
    }

    public User(){
        this.subscribedToUsers = new HashSet<>();
        this.subscribers = new HashSet<>();
        this.videoHistory = ConcurrentHashMap.newKeySet();
        this.likedVideos = new HashSet<>();
        this.disLikedVideos = new HashSet<>();
    }
}

