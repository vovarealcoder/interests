package ru.vyatkin.interests.security.jwt;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.db.service.UserService;
import ru.vyatkin.interests.security.UserAuthenticationService;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SimpleUserAuthenticationService implements UserAuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Cache<String, User> userCache = CacheBuilder.newBuilder()
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build();

    public SimpleUserAuthenticationService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<String> login(String username, String password) {
        Optional<User> user = userService.findUserByLogin(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            String uuid = UUID.randomUUID().toString();
            userCache.put(uuid, user.get());
            return Optional.of(uuid);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByToken(String token) {
        User present = userCache.getIfPresent(token);
        return Optional.ofNullable(present);
    }

    @Override
    public void logout(User user) {
        Set<Map.Entry<String, User>> entrySet = userCache.asMap().entrySet();
        for (Map.Entry<String, User> entry : entrySet) {
            if (user.getLogin().equals(entry.getValue().getLogin())) {
                userCache.invalidate(entry.getKey());
            }
        }

    }
}
