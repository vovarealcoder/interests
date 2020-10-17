package ru.vyatkin.interests.db.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "picture_object")
public class PictureObject {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pictureObject")
    private List<Picture> pictures = new ArrayList<>();

    public PictureObject(Long id, List<Picture> pictures) {
        this.id = id;
        this.pictures = pictures;
    }

    public PictureObject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureObject that = (PictureObject) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(pictures, that.pictures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pictures);
    }

    @Override
    public String toString() {
        return "PictureObject{" +
                "id=" + id +
                ", pictures=" + pictures +
                '}';
    }
}
