package ru.vyatkin.interests.db.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vyatkin.interests.DbConfig;
import ru.vyatkin.interests.InterestsApplication;
import ru.vyatkin.interests.db.entity.Picture;
import ru.vyatkin.interests.db.entity.PictureObject;
import ru.vyatkin.interests.db.entity.PictureSize;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(classes = {InterestsApplication.class, DbConfig.class})
class PictureObjectServiceTest {

    @Autowired
    private PictureObjectService pictureObjectService;

    @BeforeEach
    public void clear() {
        pictureObjectService.deleteAll();
    }

    @Test
    void getAllPictureObjects() {
        Picture picture = new Picture(null, "url1", PictureSize.P, 200, 300, null);
        Picture picture2 = new Picture(null, "url2", PictureSize.P, 200, 300, null);
        Picture picture11 = new Picture(null, "url2", PictureSize.P, 200, 300, null);
        Picture picture12 = new Picture(null, "url3", PictureSize.P, 200, 300, null);
        pictureObjectService.insertPictureObject(Arrays.asList(picture, picture2));
        pictureObjectService.insertPictureObject(Arrays.asList(picture11, picture12));
        Collection<PictureObject> pictureObjects = pictureObjectService.getAllPictureObjects();
        Assertions.assertEquals(2, pictureObjects.size());
    }

    @Test
    void insertPictureObject() {
        Picture picture = new Picture(null, "url1", PictureSize.P, 200, 300, null);
        Picture picture2 = new Picture(null, "url2", PictureSize.P, 200, 300, null);
        PictureObject object = pictureObjectService.insertPictureObject(Arrays.asList(picture, picture2));
        Optional<PictureObject> pictureObject = pictureObjectService.findPictureObject(object.getId());
        Assertions.assertTrue(pictureObject.isPresent());
    }

    @Test
    void testInsertPictureObject() {
        PictureObject pictureObject1 = new PictureObject();
        Picture picture = new Picture(null, "url1", PictureSize.P, 200, 300, pictureObject1);
        Picture picture2 = new Picture(null, "url2", PictureSize.P, 200, 300, pictureObject1);
        pictureObject1.setPictures(Arrays.asList(picture, picture2));
        PictureObject object = pictureObjectService.insertPictureObject(pictureObject1);
        Optional<PictureObject> pictureObject = pictureObjectService.findPictureObject(object.getId());
        Assertions.assertTrue(pictureObject.isPresent());
    }

    @Test
    void updatePictureObject() {
        PictureObject pictureObject1 = new PictureObject();
        Picture picture = new Picture(null, "url1", PictureSize.P, 200, 300, pictureObject1);
        Picture picture2 = new Picture(null, "url2", PictureSize.S, 200, 300, pictureObject1);
        pictureObject1.setPictures(Arrays.asList(picture, picture2));
        PictureObject object = pictureObjectService.insertPictureObject(pictureObject1);
        Optional<PictureObject> pictureObject = pictureObjectService.findPictureObject(object.getId());
        PictureObject pictureObject0 = pictureObject.get();
        Set<String> urlSet = pictureObject0.getPictures().stream().map(Picture::getUrl).collect(Collectors.toSet());
        Set<PictureSize> picSize = pictureObject0.getPictures().stream().map(Picture::getType).collect(Collectors.toSet());
        Assertions.assertTrue(urlSet.contains("url1"));
        Assertions.assertTrue(urlSet.contains("url2"));
        pictureObject0.getPictures().get(0).setUrl("url3");
        pictureObject0.getPictures().get(1).setUrl("url4");
        pictureObjectService.updatePictureObject(pictureObject0);

        Optional<PictureObject> updPic = pictureObjectService.findPictureObject(pictureObject0.getId());
        PictureObject updPic0 = updPic.get();
        Set<String> urlSetUpd = updPic0.getPictures().stream().map(Picture::getUrl).collect(Collectors.toSet());
        Assertions.assertTrue(urlSetUpd.contains("url3"));
        Assertions.assertTrue(urlSetUpd.contains("url4"));

    }


    @Test
    void deletePictureObject() {
        Picture picture = new Picture(null, "url1", PictureSize.P, 200, 300, null);
        Picture picture2 = new Picture(null, "url2", PictureSize.P, 200, 300, null);
        PictureObject object = pictureObjectService.insertPictureObject(Arrays.asList(picture, picture2));
        Optional<PictureObject> pictureObject = pictureObjectService.findPictureObject(object.getId());
        Assertions.assertTrue(pictureObject.isPresent());
        pictureObjectService.deletePictureObject(pictureObject.get().getId());
        Optional<PictureObject> pictureObject0 = pictureObjectService.findPictureObject(object.getId());
        Assertions.assertFalse(pictureObject0.isPresent());
    }

    @Test
    void exists() {
        Picture picture = new Picture(null, "url1", PictureSize.P, 200, 300, null);
        Picture picture2 = new Picture(null, "url2", PictureSize.P, 200, 300, null);
        PictureObject object = pictureObjectService.insertPictureObject(Arrays.asList(picture, picture2));
        Assertions.assertTrue(pictureObjectService.exists(object.getId()));
    }
}