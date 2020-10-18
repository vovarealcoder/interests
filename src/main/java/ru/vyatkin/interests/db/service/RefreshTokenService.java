package ru.vyatkin.interests.db.service;

import org.springframework.stereotype.Service;
import ru.vyatkin.interests.db.entity.RefreshToken;
import ru.vyatkin.interests.db.repository.RefreshTokenRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Optional<RefreshToken> findFirstByToken(String token) {
        return refreshTokenRepository.findFirstByToken(token);
    }

    public Collection<RefreshToken> findAll() {
        return refreshTokenRepository.findAll();
    }

    public Optional<RefreshToken> findById(Long id) {
        return refreshTokenRepository.findById(id);
    }

    public Optional<RefreshToken> findFirstByLogin(String login) {
        return refreshTokenRepository.findFirstByLogin(login);
    }

    public void deleteByLogin(String login) {
        Optional<RefreshToken> token = refreshTokenRepository.findFirstByLogin(login);
        token.ifPresent(refreshTokenRepository::delete);
    }

    public void deleteById(Long id) {
        refreshTokenRepository.deleteById(id);
    }

    public void deleteAll() {
        refreshTokenRepository.deleteAll();
    }

    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }
}
