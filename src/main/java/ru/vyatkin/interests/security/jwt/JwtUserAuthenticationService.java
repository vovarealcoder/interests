package ru.vyatkin.interests.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vyatkin.interests.db.entity.RefreshToken;
import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.db.service.RefreshTokenService;
import ru.vyatkin.interests.db.service.UserService;
import ru.vyatkin.interests.rest.model.AuthorizedDTO;
import ru.vyatkin.interests.security.UserAuthenticationService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class JwtUserAuthenticationService implements UserAuthenticationService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;


    public JwtUserAuthenticationService(UserService userService,
                                        RefreshTokenService refreshTokenService,
                                        PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AuthorizedDTO> login(String username, String password) {
        Optional<User> user = userService.findUserByLogin(username);
        String refreshToken = generateRefreshToken(username);
        Date accessDate = futureDate(LocalDate.now().plusDays(15));
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            String token = generateAccessToken(username, refreshToken, accessDate);
            return Optional.of(new AuthorizedDTO(refreshToken, token, accessDate));
        }
        return Optional.empty();
    }

    private Date futureDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public String refresh(String username, String refreshToken) {
        Optional<RefreshToken> token = refreshTokenService.findFirstByToken(refreshToken);
        if (token.isPresent() && username.equals(token.get().getLogin())) {
            Date expired = futureDate(LocalDate.now().plusDays(15));
            return generateAccessToken(username, refreshToken, expired);
        }
        throw new JwtException("Invalid token or username!");
    }

    private String generateRefreshToken(String username) {
        Date date = futureDate(LocalDate.now().plusMonths(6));
        String refreshToken = generateAccessToken(username, UUID.randomUUID().toString(), date);
        RefreshToken refToken = new RefreshToken(null, username, new Date(), refreshToken, new Date());
        refreshTokenService.save(refToken);
        return refreshToken;
    }

    @Override
    public Optional<User> findByToken(String token) {
        validateToken(token);
        Claims loginFromToken = getLoginFromToken(token);
        String issuer = loginFromToken.getIssuer();
        Optional<RefreshToken> refreshToken = refreshTokenService.findFirstByToken(issuer);
        if (refreshToken.isPresent()) {
            return userService.findUserByLogin(loginFromToken.getSubject());
        }
        return Optional.empty();
    }

    @Override
    public void logout(User user) {
        refreshTokenService.deleteByLogin(user.getLogin());
    }

    public String generateAccessToken(String login, String refreshToken, Date expired) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(expired)
                .setIssuer(refreshToken)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (ExpiredJwtException expEx) {
            throw new JwtException("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            throw new JwtException("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            throw new JwtException("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            throw new JwtException("Invalid signature", sEx);
        } catch (Exception e) {
            throw new JwtException("Invalid token", e);
        }
    }

    public Claims getLoginFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}
