package ru.vyatkin.interests.rest.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vyatkin.interests.db.entity.*;
import ru.vyatkin.interests.db.service.TownService;
import ru.vyatkin.interests.db.service.UserService;
import ru.vyatkin.interests.rest.model.user.RegisterUserDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
class RegisterUserDTOTest {

    @Autowired
    private UserService userService;
    @Autowired
    private TownService townService;

    @BeforeEach
    public void beforeEach() {
        userService.deleteAll();
        townService.deleteAll();
    }

    private final PasswordEncoder passwordEncoder = new PasswordEncoder() {
        @Override
        public String encode(CharSequence rawPassword) {
            return (String) rawPassword;
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return false;
        }
    };

    @Test
    void toJPA() throws ParseException {
        Date birthdate = new SimpleDateFormat("dd-MM-yyyy").parse("15-07-1994");
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("testLogin", "first",
                "last", "passwd", Gender.MALE, birthdate, "about", null, null);
        User user = RegisterUserDTO.toJPA(registerUserDTO, passwordEncoder);
        User saveUser = userService.saveUser(user);
        Optional<User> selected = userService.findUserById(saveUser.getId());
        Assertions.assertTrue(selected.isPresent());
        User selected0 = selected.get();
        Assertions.assertNull(selected0.getAvatar());
        Assertions.assertNull(selected0.getTown());
        Assertions.assertEquals("testLogin", selected0.getLogin());
        Assertions.assertEquals("first", selected0.getFirstname());
        Assertions.assertEquals("last", selected0.getLastname());
        Assertions.assertEquals("passwd", selected0.getPassword());
        Assertions.assertEquals(Gender.MALE, selected0.getGender());
        Assertions.assertEquals(birthdate, selected0.getBirthdate());
        Assertions.assertEquals("about", selected0.getAbout());
    }

    @Test
    void toJPAWithTownAvatar() throws ParseException {
        Date birthdate = new SimpleDateFormat("dd-MM-yyyy").parse("15-07-1994");
        Town town = new Town(null, "town");
        Town savedTown = townService.save(town);
        PictureDTO pictureDTO = new PictureDTO(null, "url1", PictureSize.P, 100, 200);
        PictureObjectDTO pictureObjectDTO = new PictureObjectDTO(null, Collections.singletonList(pictureDTO));
        TownDTO townDTO = new TownDTO(savedTown.getId(), savedTown.getName());
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("testLogin", "first",
                "last", "passwd", Gender.MALE, birthdate, "about", pictureObjectDTO, townDTO);
        User user = RegisterUserDTO.toJPA(registerUserDTO, passwordEncoder);
        User saveUser = userService.saveUser(user);
        Optional<User> selected = userService.findUserById(saveUser.getId());
        Assertions.assertTrue(selected.isPresent());
        User selected0 = selected.get();
        Assertions.assertEquals("url1", selected0.getAvatar().getPictures().get(0).getUrl());
        Assertions.assertEquals("town", selected0.getTown().getName());
        Assertions.assertEquals("testLogin", selected0.getLogin());
        Assertions.assertEquals("first", selected0.getFirstname());
        Assertions.assertEquals("last", selected0.getLastname());
        Assertions.assertEquals("passwd", selected0.getPassword());
        Assertions.assertEquals(Gender.MALE, selected0.getGender());
        Assertions.assertEquals(birthdate, selected0.getBirthdate());
        Assertions.assertEquals("about", selected0.getAbout());
    }
}