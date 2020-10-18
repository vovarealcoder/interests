package ru.vyatkin.interests.db.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vyatkin.interests.DbConfig;
import ru.vyatkin.interests.InterestsApplication;
import ru.vyatkin.interests.db.entity.RefreshToken;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {InterestsApplication.class, DbConfig.class})
class RefreshTokenServiceTest {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    public void cleanup() {
        refreshTokenService.deleteAll();
    }

    @Test
    void findFirstByToken() {
        RefreshToken refreshToken = new RefreshToken(null, "log",
                new Date(), "tok", new Date());
        Assertions.assertFalse(refreshTokenService.findFirstByToken("tok").isPresent());
        RefreshToken save = refreshTokenService.save(refreshToken);
        Assertions.assertTrue(refreshTokenService.findFirstByToken("tok").isPresent());
    }

    @Test
    void findAll() {
        RefreshToken refreshToken = new RefreshToken(null, "log",
                new Date(), "tok", new Date());

        RefreshToken refreshToken1 = new RefreshToken(null, "log1",
                new Date(), "tok", new Date());
        Assertions.assertTrue(refreshTokenService.findAll().isEmpty());
        refreshTokenService.save(refreshToken);
        refreshTokenService.save(refreshToken1);
        Assertions.assertFalse(refreshTokenService.findAll().isEmpty());
    }

    @Test
    void findById() {
        RefreshToken refreshToken = new RefreshToken(null, "log",
                new Date(), "tok", new Date());
        RefreshToken save = refreshTokenService.save(refreshToken);
        Assertions.assertTrue(refreshTokenService.findById(save.getId()).isPresent());
    }

    @Test
    void findFirstByLogin() {
        RefreshToken refreshToken = new RefreshToken(null, "log",
                new Date(), "tok", new Date());
        Assertions.assertFalse(refreshTokenService.findFirstByLogin("log").isPresent());
        RefreshToken save = refreshTokenService.save(refreshToken);
        Assertions.assertTrue(refreshTokenService.findFirstByLogin("log").isPresent());
    }

    @Test
    void deleteByLogin() {
        RefreshToken refreshToken = new RefreshToken(null, "log",
                new Date(), "tok", new Date());
        RefreshToken save = refreshTokenService.save(refreshToken);
        Assertions.assertTrue(refreshTokenService.findById(save.getId()).isPresent());
        refreshTokenService.deleteByLogin("log");
        Assertions.assertFalse(refreshTokenService.findById(save.getId()).isPresent());
    }

    @Test
    void deleteById() {
        RefreshToken refreshToken = new RefreshToken(null, "log",
                new Date(), "tok", new Date());
        RefreshToken save = refreshTokenService.save(refreshToken);
        Assertions.assertTrue(refreshTokenService.findById(save.getId()).isPresent());
        refreshTokenService.deleteById(save.getId());
        Assertions.assertFalse(refreshTokenService.findById(save.getId()).isPresent());
    }

    @Test
    void deleteAll() {
        RefreshToken refreshToken = new RefreshToken(null, "log",
                new Date(), "tok", new Date());
        refreshTokenService.save(refreshToken);
        Assertions.assertFalse(refreshTokenService.findAll().isEmpty());
        refreshTokenService.deleteAll();
        Assertions.assertTrue(refreshTokenService.findAll().isEmpty());
    }

    @Test
    void save() {
        RefreshToken refreshToken = new RefreshToken(null, "log",
                new Date(), "tok", new Date());
        RefreshToken save = refreshTokenService.save(refreshToken);
        Assertions.assertNotNull(save.getId());
    }
}