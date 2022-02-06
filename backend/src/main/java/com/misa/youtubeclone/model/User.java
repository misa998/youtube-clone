package com.misa.youtubeclone.model;

import com.misa.youtubeclone.dto.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(value = "User")
@Data
@NoArgsConstructor
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
    private List<String> videoHistory;
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
}
