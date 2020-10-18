package ru.vyatkin.interests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.util.LinkedMultiValueMap;
import ru.vyatkin.interests.db.entity.Category;
import ru.vyatkin.interests.db.entity.Gender;
import ru.vyatkin.interests.db.entity.RefreshToken;
import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.db.service.CategoryService;
import ru.vyatkin.interests.db.service.RefreshTokenService;
import ru.vyatkin.interests.db.service.UserService;
import ru.vyatkin.interests.rest.model.CategoryDTO;
import ru.vyatkin.interests.rest.model.common.ResponseDTO;
import ru.vyatkin.interests.rest.model.user.AuthorizedDTO;
import ru.vyatkin.interests.security.UserAuthenticationService;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {InterestsApplication.class, DbConfig.class})
public class CategoryControllerTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    public void cleanup() {
        userService.deleteAll();
        refreshTokenService.deleteAll();
    }

    @Test
    public void testGetAllUnauthorized() {
        Category category = new Category(null, "test1", "test2", null, null);
        categoryService.insertCategory(category);
        ResponseEntity<ResponseDTO<List<CategoryDTO>>> responseEntity = restTemplate.exchange("/category/all", HttpMethod.GET,
                null, new ParameterizedTypeReference<ResponseDTO<List<CategoryDTO>>>() {
                });
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
    }

    @Test
    public void testGetAll() {
        AuthorizedDTO userAndAuthorize = createUserAndAuthorize();
        Category category = new Category(null, "test1", "test2", null, null);
        categoryService.insertCategory(category);
        HttpEntity<Map<String, String>> requestEntity
                = new HttpEntity<>(new LinkedMultiValueMap<>(
                Collections.singletonMap(HttpHeaders.AUTHORIZATION,
                        Collections.singletonList(userAndAuthorize.getAccessToken()))));
        ResponseEntity<ResponseDTO<List<CategoryDTO>>> responseEntity = restTemplate.exchange("/category/all", HttpMethod.GET,
                requestEntity, new ParameterizedTypeReference<ResponseDTO<List<CategoryDTO>>>() {
                });
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(responseEntity.getBody().getPayload().isEmpty());
    }

    @Test
    public void testGetId() {
        AuthorizedDTO userAndAuthorize = createUserAndAuthorize();
        Category category0 = new Category(null, "test2", "tes32", null, null);
        Category category = categoryService.insertCategory(category0);
        HttpEntity<Map<String, String>> requestEntity
                = new HttpEntity<>(new LinkedMultiValueMap<>(
                Collections.singletonMap(HttpHeaders.AUTHORIZATION,
                        Collections.singletonList(userAndAuthorize.getAccessToken()))));
        ResponseEntity<ResponseDTO<CategoryDTO>> responseEntity = restTemplate.exchange("/category/{id}", HttpMethod.GET,
                requestEntity, new ParameterizedTypeReference<ResponseDTO<CategoryDTO>>() {
                }, category.getId());
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("test2", responseEntity.getBody().getPayload().getName());
    }

    @Test
    public void testGetParentId() {
        AuthorizedDTO userAndAuthorize = createUserAndAuthorize();
        Category category0 = new Category(null, "test3", "tes332", null, null);
        Category category = categoryService.insertCategory(category0);
        Category categoryChild = categoryService.insertCategory(new Category(null, "tes33", "tes33", category, null));
        HttpEntity<Map<String, String>> requestEntity
                = new HttpEntity<>(
                        new LinkedMultiValueMap<>(
                                Collections.singletonMap(HttpHeaders.AUTHORIZATION,
                                        Collections.singletonList(userAndAuthorize.getAccessToken()))));
        ResponseEntity<ResponseDTO<List<CategoryDTO>>> responseEntity = restTemplate.exchange("/category/parent/{id}",
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<ResponseDTO<List<CategoryDTO>>>() {
                }, category.getId());
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(responseEntity.getBody().getPayload().isEmpty());
    }

    private AuthorizedDTO createUserAndAuthorize() {
        User user = new User(null, "login", "first", "last",
                passwordEncoder.encode("passwd"), Gender.MALE,
                new Date(), null, "about", null, null, true);
        userService.saveUser(user);
        Optional<AuthorizedDTO> authorizedDTO = userAuthenticationService.login("login", "passwd");
        return authorizedDTO.get();
    }
}
