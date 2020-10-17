package ru.vyatkin.interests.db.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vyatkin.interests.DbConfig;
import ru.vyatkin.interests.InterestsApplication;
import ru.vyatkin.interests.db.entity.Town;

import java.util.Collection;
import java.util.Optional;

@SpringBootTest(classes = {InterestsApplication.class, DbConfig.class})
class TownServiceTest {

    @Autowired
    private TownService townService;

    @BeforeEach
    public void cleanup() {
        townService.deleteAll();
    }

    @Test
    void findTownById() {
        Town town = new Town();
        town.setName("testtown");
        Town save = townService.save(town);
        Optional<Town> townById = townService.findTownById(save.getId());
        Assertions.assertTrue(townById.isPresent());
    }

    @Test
    void findAll() {

        Town town = new Town();
        town.setName("testtown");
        Town save = townService.save(town);
        Town town1 = new Town();
        town1.setName("testtown1");
        Town save1 = townService.save(town1);
        Collection<Town> all = townService.findAll();
        Assertions.assertFalse(all.isEmpty());
        Assertions.assertTrue(all.contains(save));
        Assertions.assertTrue(all.contains(save1));

    }

    @Test
    void findStartsWith() {

        Town town = new Town();
        town.setName("testtown");
        Town save = townService.save(town);
        Town town1 = new Town();
        town1.setName("testtown2");
        Town save1 = townService.save(town1);
        Town town2 = new Town();
        town2.setName("stesttown");
        Town save2 = townService.save(town2);
        Collection<Town> test = townService.findStartsWith("test");
        Assertions.assertTrue(test.contains(save));
        Assertions.assertTrue(test.contains(save1));
        Assertions.assertFalse(test.contains(save2));


    }

    @Test
    void save() {
        Town town = new Town();
        town.setName("testtown");
        Town save = townService.save(town);
        Assertions.assertNotNull(save.getId());
    }

    @Test
    void delete() {
        Town town = new Town();
        town.setName("testtown");
        Town save = townService.save(town);
        Assertions.assertNotNull(save.getId());
        Assertions.assertTrue(townService.findTownById(save.getId()).isPresent());
        townService.delete(save.getId());
        Assertions.assertFalse(townService.findTownById(save.getId()).isPresent());
    }

    @Test
    void exists() {
        Town town = new Town();
        town.setName("testtown");
        Town save = townService.save(town);
        Assertions.assertNotNull(save.getId());
        Assertions.assertTrue(townService.exists(save.getId()));
    }

    @Test
    void deleteAll() {
        Town town = new Town();
        town.setName("testtown");
        Town save = townService.save(town);
        Town town1 = new Town();
        town1.setName("testtown2");
        Town save1 = townService.save(town1);
        Town town2 = new Town();
        town2.setName("stesttown");
        Town save2 = townService.save(town2);
        Assertions.assertNotNull(save.getId());
        townService.deleteAll();
        Assertions.assertTrue(townService.findAll().isEmpty());
    }

    @Test
    void uniqueTest() {
        Town town = new Town();
        town.setName("testtown");
        Town save = townService.save(town);
        Town town1 = new Town();
        town1.setName("testtown");
        Assertions.assertThrows(Exception.class, () -> townService.save(town1));
    }
}