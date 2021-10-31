package ru.yourlovedpolish.jwtapp.service.impl;

import ru.yourlovedpolish.jwtapp.model.Role;
import ru.yourlovedpolish.jwtapp.model.User;
import ru.yourlovedpolish.jwtapp.repository.RoleRepository;
import ru.yourlovedpolish.jwtapp.repository.UserRepository;
import ru.yourlovedpolish.jwtapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        System.out.println(user);

        Role role = roleRepository.findByName("ROLE_USER");
        user.setRole(role);
        User registerUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registerUser);

        return registerUser;
    }

    @Override
    public List<User> getAll()
    {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());

        return result;
    }

    @Override
    public User findByUsername(String username)
    {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id)
    {
        User result = userRepository.findById(id).orElse(null);

        if (result == null)
        {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Long id)
    {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
