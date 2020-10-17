package ru.vyatkin.interests.db.service;

import org.springframework.stereotype.Service;
import ru.vyatkin.interests.db.entity.Town;
import ru.vyatkin.interests.db.repository.TownRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class TownService {
    private final TownRepository townRepository;

    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    public Optional<Town> findTownById(Long townId) {
        return townRepository.findById(townId);
    }

    public Collection<Town> findAll() {
        return townRepository.findAll();
    }

    public Collection<Town> findStartsWith(String prefix) {
        return townRepository.findAllByNameStartsWith(prefix);
    }


    public Town save(Town town) {
        return townRepository.save(town);
    }


    public void delete(Long townId) {
        townRepository.deleteById(townId);
    }

    public boolean exists(Long townId) {
        return townRepository.existsById(townId);
    }

    public void deleteAll() {
        townRepository.deleteAll();
    }
}
