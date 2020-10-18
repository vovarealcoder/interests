package ru.vyatkin.interests.security;

import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.rest.model.user.AuthorizedDTO;

import java.util.Optional;

public interface UserAuthenticationService {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<AuthorizedDTO> login(String username, String password);


    /**
     * Refresh access token by username and access token
     *
     * @param username
     * @param refreshToken
     * @return
     */
    String refresh(String username, String refreshToken);

    /**
     * Finds a user by its dao-key.
     *
     * @param token user dao key
     * @return
     */
    Optional<User> findByToken(String token);

    /**
     * Logs out the given input {@code user}.
     *
     * @param user the user to logout
     */
    void logout(User user);
}