package ru.vyatkin.interests.rest.model.user;

import ru.vyatkin.interests.annotations.InputDTO;
import ru.vyatkin.interests.annotations.ResolveId;
import ru.vyatkin.interests.db.entity.Gender;
import ru.vyatkin.interests.db.entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@InputDTO
public class ChangeUserInfoDTO implements Serializable {
    private static final long serialVersionUID = 8162091526297253426L;

    private final Long id;
    private final String login;
    private final String firstname;
    private final String lastname;
    private final Date birthdate;
    private final Gender gender;
    private final String about;
    @ResolveId
    private final Long townId;
    @ResolveId
    private final Long avatar;

    public ChangeUserInfoDTO(Long id, String login, String firstname, String lastname,
                             Date birthdate, Gender gender, String about, Long townId,
                             Long avatar) {
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

    public Long getTownId() {
        return townId;
    }

    public Long getAvatar() {
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
        ChangeUserInfoDTO that = (ChangeUserInfoDTO) o;
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

    public static ChangeUserInfoDTO toDTO(User user) {
        Long avatar = user.getAvatar() == null
                ? null
                : user.getAvatar().getId();
        Long townDTO = user.getTown() == null ? null : user.getTown().getId();
        return new ChangeUserInfoDTO(user.getId(), user.getLogin(), user.getFirstname(), user.getLastname(),
                user.getBirthdate(), user.getGender(), user.getAbout(),
                townDTO, avatar);
    }

    public static User toJPA(User user, ChangeUserInfoDTO userInfoDTO) {
        user.setFirstname(userInfoDTO.firstname);
        user.setLastname(userInfoDTO.lastname);
        user.setBirthdate(userInfoDTO.birthdate);
        user.setGender(userInfoDTO.gender);
        user.setAbout(userInfoDTO.about);
        return user;
    }
}
