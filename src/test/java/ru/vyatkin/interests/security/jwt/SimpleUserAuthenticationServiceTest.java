package ru.vyatkin.interests.security.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vyatkin.interests.db.entity.Gender;
import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.db.service.UserService;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimpleUserAuthenticationServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SimpleUserAuthenticationService simpleUserAuthenticationService;
    @BeforeEach
    public void cleanup() {
        userService.deleteAll();
    }

    @Test
    public void testService() {
        User user = new User(null, "login1", "first", "last",
                passwordEncoder.encode("passwd"), Gender.MALE, new Date(),
                null, "about", null, null, true);
        User saveUser = userService.saveUser(user);
        Optional<String> login = simpleUserAuthenticationService.login("login1", "passwd");
        Assertions.assertTrue(login.isPresent());
        Optional<User> byToken = simpleUserAuthenticationService.findByToken(login.get());
        Assertions.assertEquals("login1", byToken.get().getLogin());

    }
}