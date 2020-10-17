package ru.vyatkin.interests.db.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vyatkin.interests.db.entity.User;
import ru.vyatkin.interests.db.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findUserByLogin(String userName) {
        return userRepository.findFirstByLogin(userName);
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public void existsById(Long userId) {
        userRepository.existsById(userId);
    }


    public void deleteAll() {
        userRepository.deleteAll();
    }

}
