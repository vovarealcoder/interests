package ru.vyatkin.interests.rest.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vyatkin.interests.db.entity.Picture;
import ru.vyatkin.interests.db.entity.PictureObject;
import ru.vyatkin.interests.db.entity.PictureSize;
import ru.vyatkin.interests.db.service.PictureObjectService;
import ru.vyatkin.interests.rest.model.PictureObjectDTO;
import ru.vyatkin.interests.rest.model.common.ResponseDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/picture")
public class PictureController {
    @Value("${picture.folder}")
    private String pictureFolder;
    private final PictureObjectService pictureObjectService;

    public PictureController(PictureObjectService pictureObjectService) {
        this.pictureObjectService = pictureObjectService;
    }

    @GetMapping("/picture/{id}/{size}")
    public void getPicture(@PathVariable("id") Long id,
                           @PathVariable("size") PictureSize pictureSize,
                           HttpServletResponse httpServletResponse) {
        Optional<PictureObject> pictureObject = pictureObjectService.findPictureObject(id);
        if (!pictureObject.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        List<Picture> pictures = pictureObject.get().getPictures();
        Optional<Picture> picture = pictures.stream()
                .filter(it -> pictureSize.equals(it.getType()))
                .findFirst();
        if (!picture.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        File file = Paths.get(pictureFolder).toFile();
        String filename = picture.get().getFilename();
        File filePic = new File(file, filename);
        if (!filePic.exists()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try (FileInputStream fileInputStream = new FileInputStream(filePic)) {
            IOUtils.copy(fileInputStream, httpServletResponse.getOutputStream());
            httpServletResponse.flushBuffer();
        } catch (FileNotFoundException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        } else if (filename.endsWith(".gif")) {
            httpServletResponse.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else if (filename.endsWith(".png")) {
            httpServletResponse.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<PictureObjectDTO> uploadFileHandler(@RequestParam("name") String name,
                                                           @RequestParam("file") MultipartFile file) {
        //todo ...
        return null;
    }
}

