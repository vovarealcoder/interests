package ru.vyatkin.interests.rest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AuthorizedDTO implements Serializable {
    private static final long serialVersionUID = -2167629519147183752L;
    private final String refreshToken;
    private final String accessToken;
    private final Date expiredIn;

    public AuthorizedDTO(String refreshToken, String accessToken, Date expiredIn) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.expiredIn = expiredIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Date getExpiredIn() {
        return expiredIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizedDTO that = (AuthorizedDTO) o;
        return Objects.equals(refreshToken, that.refreshToken) &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(expiredIn, that.expiredIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken, accessToken, expiredIn);
    }

    @Override
    public String toString() {
        return "AuthorizedDTO{" +
                "refreshToken='" + refreshToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expiredIn=" + expiredIn +
                '}';
    }
}
