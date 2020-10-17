package ru.vyatkin.interests.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vyatkin.interests.db.entity.Picture;
import ru.vyatkin.interests.db.entity.PictureObject;

import java.util.Collection;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    Collection<Picture> findByPictureObject(PictureObject pictureObject);
}
