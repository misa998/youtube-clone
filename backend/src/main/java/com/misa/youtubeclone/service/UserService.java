package com.misa.youtubeclone.service;

import com.misa.youtubeclone.dto.UserDto;
import com.misa.youtubeclone.model.User;
import com.misa.youtubeclone.model.exceptions.UserNotFoundById;
import com.misa.youtubeclone.model.exceptions.UserNotFoundBySub;
import com.misa.youtubeclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String sub = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getClaim("sub");
        return userRepository.findBySub(sub).orElseThrow(() -> new UserNotFoundBySub(sub));
    }

    public void addToLikedVideos(String videoId) {
        UserDto curr = new UserDto(getCurrentUser());
        curr.addToLikedVideos(videoId);
        userRepository.save(curr.getUser());
    }

    public boolean isLikedVideo(String videoId) {
        return Optional.ofNullable(getCurrentUser().getLikedVideos()).orElse(ConcurrentHashMap.newKeySet()).stream().anyMatch(e -> e.equals(videoId));
    }

    public boolean isDislikedVideo(String videoId) {
        return Optional.ofNullable(getCurrentUser().getDisLikedVideos()).orElse(ConcurrentHashMap.newKeySet()).stream().anyMatch(e -> e.equals(videoId));
    }

    public void removeLikedVideo(String videoId) {
        UserDto curr = new UserDto(getCurrentUser());
        curr.removeLikedVideo(videoId);
        userRepository.save(curr.getUser());
    }

    public void removeDislikedVideo(String videoId) {
        UserDto curr = new UserDto(getCurrentUser());
        curr.removeDislikedVideo(videoId);
        userRepository.save(curr.getUser());
    }

    public void addToDislikedVideo(String videoId) {
        UserDto curr = new UserDto(getCurrentUser());
        curr.addToDislikedVideos(videoId);
        userRepository.save(curr.getUser());
    }

    public void addVideoToHistory(String videoId) {
        var current = new UserDto(getCurrentUser());
        current.addToVideoHistory(videoId);
        userRepository.save(current.getUser());
    }

    public void subscribe(String userId) {
        var currentUserDto = new UserDto(getCurrentUser());
        currentUserDto.addToSubscribedToUsers(userId);

        var userToSubDto = new UserDto(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundById(userId)));
        userToSubDto.addToSubscribers(userId);

        userRepository.save(currentUserDto.getUser());
        userRepository.save(userToSubDto.getUser());
    }

    public void unSubscribe(String userId) {
        var currentUserDto = new UserDto(getCurrentUser());
        currentUserDto.removeFromSubscribedToUsers(userId);

        var userToSubDto = new UserDto(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundById(userId)));
        userToSubDto.removeFromSubscribers(userId);

        userRepository.save(currentUserDto.getUser());
        userRepository.save(userToSubDto.getUser());
    }

    public Set<String> getUserHistory(String userId) {
        var userDto = new UserDto(getCurrentUser());
        return userDto.getVideoHistory();
    }
}
