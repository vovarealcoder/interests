package ru.vyatkin.interests.rest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.db.service.UserService;
import ru.vyatkin.interests.rest.model.RegisterUserDTO;
import ru.vyatkin.interests.rest.model.common.NoContentDTO;
import ru.vyatkin.interests.rest.model.common.ResponseDTO;
import ru.vyatkin.interests.security.UserAuthenticationService;

import java.util.Collections;
import java.util.Map;

import static ru.vyatkin.interests.rest.model.common.RequestStatus.OK;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserAuthenticationService userAuthenticationService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserAuthenticationService userAuthenticationService,
                          UserService userService, PasswordEncoder passwordEncoder) {
        this.userAuthenticationService = userAuthenticationService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login, @RequestParam("password") String password) {
        //todo login attempt counting
        return userAuthenticationService.login(login, password).orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }

    @PostMapping("/register")
    public ResponseDTO<Map<String, Long>> register(@RequestParam("params") RegisterUserDTO registerUserDTO) {
        User user = userService.saveUser(RegisterUserDTO.toJPA(registerUserDTO, passwordEncoder));
        return new ResponseDTO<>(OK, "Success", Collections.singletonMap("userId", user.getId()));
    }
}