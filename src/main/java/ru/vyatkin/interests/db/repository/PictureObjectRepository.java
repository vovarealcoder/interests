package ru.vyatkin.interests.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vyatkin.interests.db.entity.Picture;
import ru.vyatkin.interests.db.entity.PictureObject;
import sun.text.resources.CollationData;

@RequestMapping
public interface PictureObjectRepository extends JpaRepository<PictureObject, Long> {
}
