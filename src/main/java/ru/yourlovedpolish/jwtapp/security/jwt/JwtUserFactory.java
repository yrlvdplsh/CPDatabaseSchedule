package ru.yourlovedpolish.jwtapp.security.jwt;

import ru.yourlovedpolish.jwtapp.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class JwtUserFactory
{
    public JwtUserFactory() { }

    public static JwtUser create(User user)
    {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                true,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()))
        );
    }
}