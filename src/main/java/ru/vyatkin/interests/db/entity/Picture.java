package ru.vyatkin.interests.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "picture")
public class Picture {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String filename;

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

    public Picture(Long id, String filename, PictureSize type, Integer width, Integer height, PictureObject pictureObject) {
        this.id = id;
        this.filename = filename;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String url) {
        this.filename = url;
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
                Objects.equals(filename, picture.filename) &&
                type == picture.type &&
                Objects.equals(width, picture.width) &&
                Objects.equals(height, picture.height) &&
                Objects.equals(pictureObject, picture.pictureObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filename, type, width, height, pictureObject);
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", url='" + filename + '\'' +
                ", type=" + type +
                ", width=" + width +
                ", height=" + height +
                ", pictureObject=" + pictureObject +
                '}';
    }
}
