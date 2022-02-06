package com.misa.youtubeclone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misa.youtubeclone.dto.UserInfoDTO;
import com.misa.youtubeclone.model.User;
import com.misa.youtubeclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    @Value("${auth0.userinfoEndpoint}")
    private String userinfoEndpoint;

    private final UserRepository userRepo;

    /**
     * Registers the user by sending GET request for the userinfo with the provided token
     * and saving the user to the database.
     * @param tokenValue String value sent with the REST request.
     * @throws IOException
     * @throws InterruptedException
     */
    public void registerUser(String tokenValue) throws IOException, InterruptedException {
        HttpResponse<String> responseString = getUserInfoResponse(tokenValue);
        UserInfoDTO userInfoDTO = getUserInfoFromResponse(responseString.body());
        userRepo.save(new User(userInfoDTO));
    }

    /**
     * @param tokenValue String value sent with the REST request.
     * @return HttpResponse object return from sending the auth userinfo GET request.
     * @throws IOException
     * @throws InterruptedException
     */
    private HttpResponse<String> getUserInfoResponse(String tokenValue) throws IOException, InterruptedException {
        var httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userinfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * @param body String object returned from the HttpResponse body.
     * @return UserInfoDTO object mapped from the body.
     * @throws JsonProcessingException
     */
    private UserInfoDTO getUserInfoFromResponse(String body) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(body, UserInfoDTO.class);
    }
}
