package ru.yourlovedpolish.jwtapp.security;

import ru.yourlovedpolish.jwtapp.model.User;
import ru.yourlovedpolish.jwtapp.security.jwt.JwtUser;
import ru.yourlovedpolish.jwtapp.security.jwt.JwtUserFactory;
import ru.yourlovedpolish.jwtapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService
{
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userService.findByUsername(username);

        if (user == null)
        {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("loadByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}
