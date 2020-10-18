package ru.vyatkin.interests.db.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@Entity(name = "refresh_token")
public class RefreshToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* restrict changes for login */
    @Column(name = "login", updatable = false, unique = true, nullable = false)
    private String login;

    @Column(name = "token")
    private String token;

    /* exclude from modification queries for insert default now() value on creation */
    @Column(name = "created", insertable = false, updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "last_refresh")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRefresh;

    public RefreshToken(Long id, String login, Date created, String token,Date lastRefresh) {
        this.id = id;
        this.login = login;
        this.created = created;
        this.token = token;
        this.lastRefresh = lastRefresh;
    }

    public RefreshToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login) &&
                Objects.equals(token, that.token) &&
                Objects.equals(created, that.created) &&
                Objects.equals(lastRefresh, that.lastRefresh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, token, created, lastRefresh);
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", token='" + token + '\'' +
                ", created=" + created +
                ", lastRefresh=" + lastRefresh +
                '}';
    }
}
