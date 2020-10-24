package ru.vyatkin.interests.rest.model;

import ru.vyatkin.interests.annotations.Subtype;
import ru.vyatkin.interests.db.entity.Picture;
import ru.vyatkin.interests.db.entity.PictureSize;

import java.io.Serializable;
import java.util.Objects;

@Subtype
public class PictureDTO implements Serializable {
    private static final long serialVersionUID = 1986994737709387836L;
    private final Long id;
    private final String url;
    private final PictureSize type;
    private final Integer width;
    private final Integer height;

    public PictureDTO(Long id, String url, PictureSize type, Integer width, Integer height) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public PictureSize getType() {
        return type;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureDTO that = (PictureDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(url, that.url) &&
                type == that.type &&
                Objects.equals(width, that.width) &&
                Objects.equals(height, that.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, type, width, height);
    }

    @Override
    public String toString() {
        return "PictureDTO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public static Picture toJpa(PictureDTO pictureDTO) {
        return new Picture(pictureDTO.id, pictureDTO.url,
                pictureDTO.type, pictureDTO.width, pictureDTO.height, null);
    }

    public static PictureDTO toDTO(Picture picture) {
        return new PictureDTO(picture.getId(), picture.getFilename(),
                picture.getType(), picture.getWidth(), picture.getHeight());
    }

}
