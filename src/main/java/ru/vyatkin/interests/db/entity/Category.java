package ru.vyatkin.interests.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "category")
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private PictureObject pictureObject;

    public Category() {
    }

    public Category(Long id, String name, String description, Category parent, PictureObject pictureObject) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.pictureObject = pictureObject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
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
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name) &&
                Objects.equals(description, category.description) &&
                Objects.equals(parent, category.parent) &&
                Objects.equals(pictureObject, category.pictureObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, parent, pictureObject);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parent=" + parent +
                ", pictureObject=" + pictureObject +
                '}';
    }
}
