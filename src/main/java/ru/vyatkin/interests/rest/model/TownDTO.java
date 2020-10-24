package ru.vyatkin.interests.rest.model;

import ru.vyatkin.interests.annotations.Subtype;
import ru.vyatkin.interests.db.entity.Town;

import java.io.Serializable;
import java.util.Objects;

@Subtype
public class TownDTO implements Serializable {
    private static final long serialVersionUID = -5989794996159971233L;
    private final Long id;
    private final String name;

    public TownDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TownDTO townDTO = (TownDTO) o;
        return Objects.equals(id, townDTO.id) &&
                Objects.equals(name, townDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TownDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static TownDTO toDTO(Town town) {
        return new TownDTO(town.getId(), town.getName());
    }


    public static Town toJPA(TownDTO object) {
        return new Town(object.getId(), object.getName());
    }

}
