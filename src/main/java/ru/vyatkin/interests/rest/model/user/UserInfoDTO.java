package ru.vyatkin.interests.rest.model.user;

import ru.vyatkin.interests.db.entity.Gender;
import ru.vyatkin.interests.db.entity.PictureObject;
import ru.vyatkin.interests.db.entity.Town;
import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.rest.model.PictureObjectDTO;
import ru.vyatkin.interests.rest.model.TownDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class UserInfoDTO implements Serializable {
    private static final long serialVersionUID = 8162091526297253426L;

    private final Long id;
    private final String login;
    private final String firstname;
    private final String lastname;
    private final Date birthdate;
    private final Gender gender;
    private final String about;
    private final TownDTO townId;
    private final PictureObjectDTO avatar;

    public UserInfoDTO(Long id, String login, String firstname, String lastname,
                       Date birthdate, Gender gender, String about, TownDTO townId,
                       PictureObjectDTO avatar) {
        this.id = id;
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.about = about;
        this.townId = townId;
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getAbout() {
        return about;
    }

    public TownDTO getTownId() {
        return townId;
    }

    public PictureObjectDTO getAvatar() {
        return avatar;
    }

    public Long getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfoDTO that = (UserInfoDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(birthdate, that.birthdate) &&
                gender == that.gender &&
                Objects.equals(about, that.about) &&
                Objects.equals(townId, that.townId) &&
                Objects.equals(avatar, that.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, firstname, lastname, birthdate, gender,
                about, townId, avatar);
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthdate=" + birthdate +
                ", gender=" + gender +
                ", about='" + about + '\'' +
                ", townId=" + townId +
                ", avatarId=" + avatar +
                '}';
    }

    public static UserInfoDTO toDTO(User user) {
        PictureObjectDTO avatar = user.getAvatar() == null
                ? null
                : PictureObjectDTO.toDTO(user.getAvatar());
        TownDTO townDTO = user.getTown() == null ? null : TownDTO.toDTO(user.getTown());
        return new UserInfoDTO(user.getId(), user.getLogin(), user.getFirstname(), user.getLastname(),
                user.getBirthdate(), user.getGender(), user.getAbout(),
                townDTO, avatar);
    }

    public static User toJPA(User user, UserInfoDTO userInfoDTO) {
        user.setFirstname(userInfoDTO.firstname);
        user.setLastname(userInfoDTO.lastname);
        user.setBirthdate(userInfoDTO.birthdate);
        user.setGender(userInfoDTO.gender);
        user.setAbout(userInfoDTO.about);
        Town townId = userInfoDTO.townId == null ? null : TownDTO.toJPA(userInfoDTO.townId);
        user.setTown(townId);
        PictureObject pictureObject = userInfoDTO.avatar == null ? null : PictureObjectDTO.toJpa(userInfoDTO.avatar);
        user.setAvatar(pictureObject);
        return user;
    }
}
