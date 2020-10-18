package ru.vyatkin.interests.security.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vyatkin.interests.DbConfig;
import ru.vyatkin.interests.InterestsApplication;
import ru.vyatkin.interests.db.entity.Gender;
import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.db.service.UserService;
import ru.vyatkin.interests.rest.model.user.AuthorizedDTO;
import ru.vyatkin.interests.security.UserAuthenticationService;

import java.util.Date;
import java.util.Optional;

@SpringBootTest(classes = {InterestsApplication.class, DbConfig.class})
class JwtUserAuthenticationServiceTest {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void cleanup() {
        userService.deleteAll();
    }

    @Test
    void login() {
        User user = new User(null, "login", "first", "last",
                passwordEncoder.encode("passwd"), Gender.MALE,
                new Date(), null, "about", null, null, true);
        userService.saveUser(user);
        Optional<AuthorizedDTO> login = userAuthenticationService.login("login", "passwd");
        Assertions.assertTrue(login.isPresent());
        Assertions.assertNotNull(login.get().getAccessToken());
        Assertions.assertNotNull(login.get().getRefreshToken());
        Assertions.assertTrue(login.get().getExpiredIn().after(new Date()));
        User user0 = userAuthenticationService.findByToken(login.get().getAccessToken()).get();
        Assertions.assertEquals("login", user0.getLogin());
    }

    @Test
    void refresh() {
        User user = new User(null, "login", "first", "last",
                passwordEncoder.encode("passwd"), Gender.MALE,
                new Date(), null, "about", null, null, true);
        userService.saveUser(user);
        Optional<AuthorizedDTO> login = userAuthenticationService.login("login", "passwd");
        Assertions.assertTrue(login.isPresent());
        User user0 = userAuthenticationService.findByToken(login.get().getAccessToken()).get();
        Assertions.assertEquals("login", user0.getLogin());
        String refreshed = userAuthenticationService.refresh("login", login.get().getRefreshToken());
        User userRefreshed = userAuthenticationService.findByToken(refreshed).get();
        Assertions.assertEquals("login", userRefreshed.getLogin());
    }

    @Test
    void logout() {
        User user = new User(null, "login", "first", "last",
                passwordEncoder.encode("passwd"), Gender.MALE,
                new Date(), null, "about", null, null, true);
        userService.saveUser(user);
        Optional<AuthorizedDTO> login = userAuthenticationService.login("login", "passwd");
        Assertions.assertTrue(login.isPresent());
        User user0 = userAuthenticationService.findByToken(login.get().getAccessToken()).get();
        Assertions.assertEquals("login", user0.getLogin());
        userAuthenticationService.logout(user0);
        Optional<User> userLogout = userAuthenticationService.findByToken(login.get().getAccessToken());
        Assertions.assertFalse(userLogout.isPresent());
    }
}