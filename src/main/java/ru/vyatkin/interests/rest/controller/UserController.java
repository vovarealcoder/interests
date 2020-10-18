package ru.vyatkin.interests.rest.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.db.service.UserService;
import ru.vyatkin.interests.rest.model.user.AuthorizedDTO;
import ru.vyatkin.interests.rest.model.user.RegisterUserDTO;
import ru.vyatkin.interests.rest.model.common.RequestStatus;
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
    public ResponseDTO<AuthorizedDTO> login(@RequestParam("login") String login,
                                            @RequestParam("password") String password) {
        //todo login attempt counting
        return new ResponseDTO<>(RequestStatus.OK,
                "Success", userAuthenticationService.login(login, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password")));
    }

    @PostMapping("/refresh")
    public ResponseDTO<Map<String, String>> refresh(@RequestParam("login") String login,
                                                    @RequestParam("refreshtoken") String refreshtoken) {
        String refresh = userAuthenticationService.refresh(login, refreshtoken);
        return new ResponseDTO<>(RequestStatus.OK,
                "Success", Collections.singletonMap("accessToken", refresh));
    }

    @PostMapping("/register")
    public ResponseDTO<Map<String, Long>> register(@RequestBody RegisterUserDTO registerUserDTO) {
        User user = userService.saveUser(RegisterUserDTO.toJPA(registerUserDTO, passwordEncoder));
        return new ResponseDTO<>(OK, "Success", Collections.singletonMap("userId", user.getId()));
    }
}
