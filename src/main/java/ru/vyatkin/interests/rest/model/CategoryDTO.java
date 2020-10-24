package ru.vyatkin.interests.rest.model;

import ru.vyatkin.interests.db.entity.Category;
import ru.vyatkin.interests.db.entity.PictureObject;

import java.io.Serializable;
import java.util.Objects;

public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 3329719514866111106L;
    private final Long id;
    private final String name;
    private final String description;
    private final CategoryDTO parent;
    private final PictureObjectDTO pictureObject;

    public CategoryDTO(Long id, String name, String description, CategoryDTO parent,
                       PictureObjectDTO pictureObject) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.pictureObject = pictureObject;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CategoryDTO getParent() {
        return parent;
    }

    public PictureObjectDTO getPictureObject() {
        return pictureObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(parent, that.parent) &&
                Objects.equals(pictureObject, that.pictureObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, parent, pictureObject);
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parent=" + parent +
                ", pictureObject=" + pictureObject +
                '}';
    }

    public static CategoryDTO toDTO(Category category) {
        CategoryDTO parent = category.getParent() == null ? null : CategoryDTO.toDTO(category.getParent());
        PictureObjectDTO pictureObject = category.getPictureObject() == null
                ? null
                : PictureObjectDTO.toDTO(category.getPictureObject());
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription(), parent, pictureObject);
    }


    public static Category toJPA(CategoryDTO categoryDTO) {
        PictureObject pictureObject = categoryDTO.pictureObject == null
                ? null
                : PictureObjectDTO.toJpa(categoryDTO.pictureObject);
        Category parent = categoryDTO.parent == null
                ? null
                : CategoryDTO.toJPA(categoryDTO.parent);
        return new Category(categoryDTO.id, categoryDTO.name, categoryDTO.description, parent, pictureObject);
    }
}
