package ru.vyatkin.interests.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.vyatkin.interests.DbConfig;
import ru.vyatkin.interests.InterestsApplication;
import ru.vyatkin.interests.db.entity.Gender;
import ru.vyatkin.interests.db.service.UserService;
import ru.vyatkin.interests.rest.model.common.ResponseDTO;
import ru.vyatkin.interests.rest.model.user.AuthorizedDTO;
import ru.vyatkin.interests.rest.model.user.RegisterUserDTO;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {InterestsApplication.class, DbConfig.class})
class UserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void cleanup() {
        userService.deleteAll();
    }


    @Test
    void registerAndLogin() {
        //check register
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("login12", "first",
                "last", "passwd", Gender.MALE, new Date(), "about", null, null);
        ResponseEntity<ResponseDTO<Map<String, Long>>> responseEntity = testRestTemplate.exchange("/user/register", HttpMethod.POST,
                new HttpEntity<>(registerUserDTO), new ParameterizedTypeReference<ResponseDTO<Map<String, Long>>>() {
                });
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(responseEntity.getBody().getPayload().get("userId"));
        //check login
        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put("login", Collections.singletonList("login12"));
        multiValueMap.put("password", Collections.singletonList("passwd"));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(multiValueMap, new LinkedMultiValueMap<>());
        ResponseEntity<ResponseDTO<AuthorizedDTO>> responseEntityAuth
                = testRestTemplate.exchange("/user/login", HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ResponseDTO<AuthorizedDTO>>() {
                });
        Assertions.assertTrue(responseEntityAuth.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(responseEntityAuth.getBody().getPayload().getAccessToken());

        //refresh
        LinkedMultiValueMap<String, String> multiValueMapRef = new LinkedMultiValueMap<>();
        multiValueMapRef.put("login", Collections.singletonList("login12"));
        String refreshToken = responseEntityAuth.getBody().getPayload().getRefreshToken();
        multiValueMapRef.put("refreshtoken", Collections.singletonList(refreshToken));
        HttpEntity<MultiValueMap<String, String>> requestEntityRef = new HttpEntity<>(multiValueMapRef, new LinkedMultiValueMap<>());
        ResponseEntity<ResponseDTO<Map<String, String>>> refreshResponse
                = testRestTemplate.exchange("/user/refresh", HttpMethod.POST,
                requestEntityRef,
                new ParameterizedTypeReference<ResponseDTO<Map<String, String>>>() {
                });
        Assertions.assertTrue(refreshResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(refreshResponse.getBody().getPayload().get("accessToken"));
    }
}