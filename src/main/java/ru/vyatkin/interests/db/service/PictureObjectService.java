package ru.vyatkin.interests.db.service;

import org.springframework.stereotype.Service;
import ru.vyatkin.interests.db.entity.Category;
import ru.vyatkin.interests.db.entity.Picture;
import ru.vyatkin.interests.db.entity.PictureObject;
import ru.vyatkin.interests.db.repository.CategoryRepository;
import ru.vyatkin.interests.db.repository.PictureObjectRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class PictureObjectService {
    private final PictureObjectRepository pictureObjectRepository;

    public PictureObjectService(PictureObjectRepository pictureObjectRepository) {
        this.pictureObjectRepository = pictureObjectRepository;
    }

    public Collection<PictureObject> getAllPictureObjects() {
        return pictureObjectRepository.findAll();
    }

    public PictureObject insertPictureObject(PictureObject pictureObject) {
        return pictureObjectRepository.save(pictureObject);
    }

    public PictureObject insertPictureObject(Collection<Picture> pictures) {
        PictureObject pictureObject = new PictureObject();
        for (Picture picture : pictures) {
            picture.setPictureObject(pictureObject);
        }
        return pictureObjectRepository.save(pictureObject);
    }

    public PictureObject updatePictureObject(PictureObject pictureObject) {
        return pictureObjectRepository.save(pictureObject);
    }

    public Optional<PictureObject> findPictureObject(Long pictureObjectId) {
        return pictureObjectRepository.findById(pictureObjectId);
    }

    public void deletePictureObject(Long pictureObjectId) {
        pictureObjectRepository.deleteById(pictureObjectId);
    }

    public boolean exists(Long pictureObjectId) {
        return pictureObjectRepository.existsById(pictureObjectId);
    }

    public void deleteAll() {
        pictureObjectRepository.deleteAll();
    }
}
