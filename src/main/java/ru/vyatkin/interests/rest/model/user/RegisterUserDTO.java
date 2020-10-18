package ru.vyatkin.interests.rest.model.user;

import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vyatkin.interests.db.entity.Gender;
import ru.vyatkin.interests.db.entity.PictureObject;
import ru.vyatkin.interests.db.entity.Town;
import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.rest.model.PictureObjectDTO;
import ru.vyatkin.interests.rest.model.TownDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class RegisterUserDTO implements Serializable {
    private static final long serialVersionUID = -1882016312543430759L;
    private final String login;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final Gender gender;
    private final Date birthdate;
    private final String about;
    private final PictureObjectDTO avatar;
    private final TownDTO town;

    public RegisterUserDTO(String login, String firstname, String lastname,
                           String password, Gender gender, Date birthdate,
                           String about, PictureObjectDTO avatar, TownDTO town) {
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.gender = gender;
        this.birthdate = birthdate;
        this.about = about;
        this.avatar = avatar;
        this.town = town;
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

    public String getPassword() {
        return password;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getAbout() {
        return about;
    }

    public PictureObjectDTO getAvatar() {
        return avatar;
    }

    public TownDTO getTown() {
        return town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterUserDTO that = (RegisterUserDTO) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(password, that.password) &&
                gender == that.gender &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(about, that.about) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(town, that.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, firstname, lastname, password, gender,
                birthdate, about, avatar, town);
    }

    @Override
    public String toString() {
        return "RegisterUserDTO{" +
                "login='" + login + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", birthdate=" + birthdate +
                ", about='" + about + '\'' +
                ", avatar=" + avatar +
                ", town=" + town +
                '}';
    }

    public static User toJPA(RegisterUserDTO registerUserDTO, @Nullable PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setLogin(registerUserDTO.login);
        user.setFirstname(registerUserDTO.firstname);
        user.setLastname(registerUserDTO.lastname);
        user.setPassword(registerUserDTO.login);
        if (passwordEncoder == null) {
            user.setPassword(registerUserDTO.password);
        } else {
            user.setPassword(passwordEncoder.encode(registerUserDTO.password));
        }
        user.setGender(registerUserDTO.gender);
        user.setBirthdate(registerUserDTO.birthdate);
        user.setAbout(registerUserDTO.about);
        user.setActive(false);
        PictureObject avatar = registerUserDTO.avatar == null
                ? null
                : PictureObjectDTO.toJpa(registerUserDTO.avatar);
        user.setAvatar(avatar);
        Town town = registerUserDTO.avatar == null ? null : TownDTO.toJPA(registerUserDTO.town);
        user.setTown(town);
        return user;
    }
}
