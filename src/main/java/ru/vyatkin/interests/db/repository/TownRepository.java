package ru.vyatkin.interests.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vyatkin.interests.db.entity.Town;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    Optional<Town> findFirstByName(String name);

    Collection<Town> findAllByNameStartsWith(String name);
}
