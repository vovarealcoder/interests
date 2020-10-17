package ru.vyatkin.interests.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "picture")
public class Picture {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private PictureSize type;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_object_id")
    private PictureObject pictureObject;

    public Picture(Long id, String url, PictureSize type, Integer width, Integer height, PictureObject pictureObject) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.width = width;
        this.height = height;
        this.pictureObject = pictureObject;
    }

    public Picture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PictureSize getType() {
        return type;
    }

    public void setType(PictureSize type) {
        this.type = type;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public PictureObject getPictureObject() {
        return pictureObject;
    }

    public void setPictureObject(PictureObject pictureObject) {
        this.pictureObject = pictureObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picture picture = (Picture) o;
        return Objects.equals(id, picture.id) &&
                Objects.equals(url, picture.url) &&
                type == picture.type &&
                Objects.equals(width, picture.width) &&
                Objects.equals(height, picture.height) &&
                Objects.equals(pictureObject, picture.pictureObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, type, width, height, pictureObject);
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", width=" + width +
                ", height=" + height +
                ", pictureObject=" + pictureObject +
                '}';
    }
}
