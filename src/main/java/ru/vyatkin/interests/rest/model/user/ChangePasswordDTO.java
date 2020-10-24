package ru.vyatkin.interests.rest.model.user;

import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vyatkin.interests.annotations.InputDTO;
import ru.vyatkin.interests.db.entity.User;

import java.io.Serializable;
import java.util.Objects;

@InputDTO
public class ChangePasswordDTO implements Serializable {
    private static final long serialVersionUID = 7936580417134308925L;

    private final String login;
    private final String oldPassword;
    private final String newPassword;

    public ChangePasswordDTO(String login, String oldPassword, String newPassword) {
        this.login = login;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getLogin() {
        return login;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangePasswordDTO that = (ChangePasswordDTO) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(oldPassword, that.oldPassword) &&
                Objects.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, oldPassword, newPassword);
    }

    @Override
    public String toString() {
        return "ChangePasswordDTO{" +
                "login='" + login + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }

    public static User toJpa(ChangePasswordDTO changePasswordDTO, User user,
                             @Nullable PasswordEncoder passwordEncoder) {
         if (passwordEncoder != null) {
            user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword));
         } else {
            user.setPassword(changePasswordDTO.newPassword);
         }
        return user;
    }
}
