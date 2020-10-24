package ru.vyatkin.interests.rest.model;

import ru.vyatkin.interests.annotations.Subtype;
import ru.vyatkin.interests.db.entity.Picture;
import ru.vyatkin.interests.db.entity.PictureObject;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Subtype
public class PictureObjectDTO implements Serializable {
    private static final long serialVersionUID = -5919886322536999747L;
    private final Long id;
    private final List<PictureDTO> pictureDTO;

    public PictureObjectDTO(Long id, List<PictureDTO> pictureDTO) {
        this.id = id;
        this.pictureDTO = pictureDTO;
    }

    public Long getId() {
        return id;
    }

    public List<PictureDTO> getPictureDTO() {
        return pictureDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureObjectDTO that = (PictureObjectDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(pictureDTO, that.pictureDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pictureDTO);
    }

    @Override
    public String toString() {
        return "PictureObjectDTO{" +
                "id=" + id +
                ", pictureDTO=" + pictureDTO +
                '}';
    }

    public static PictureObject toJpa(PictureObjectDTO pictureObjectDTO) {
        List<Picture> pictures = pictureObjectDTO.pictureDTO.stream().map(PictureDTO::toJpa)
                .collect(Collectors.toList());
        final PictureObject pictureObject = new PictureObject(pictureObjectDTO.id, pictures);
        pictures.forEach(picture -> picture.setPictureObject(pictureObject));
        return pictureObject;
    }

    public static PictureObjectDTO toDTO(PictureObject pictureObject) {
        List<PictureDTO> pictures = pictureObject.getPictures().stream().map(PictureDTO::toDTO)
                .collect(Collectors.toList());
        return new PictureObjectDTO(pictureObject.getId(), pictures);
    }

}
