package ru.yourlovedpolish.jwtapp.rest;

import ru.yourlovedpolish.jwtapp.dto.AuthenticationRequestDto;
import ru.yourlovedpolish.jwtapp.dto.RegistrationRequestDto;
import ru.yourlovedpolish.jwtapp.model.User;
import ru.yourlovedpolish.jwtapp.security.jwt.JwtTokenProvider;
import ru.yourlovedpolish.jwtapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/")
public class AuthenticationRestController
{
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService)
    {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("register")
    public Object register(@RequestBody RegistrationRequestDto registrationRequestDto)
    {
        User user = new User();
        user.setUsername(registrationRequestDto.getUsername());
        user.setPassword(registrationRequestDto.getPassword());
        try
        {
            userService.register(user);
            return "OK";
        } catch (Exception e)
        {
            log.info("Register - Username: {} is busy", user.getUsername());
            return "NO";
        }

    }

    @PostMapping("auth")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto)
    {
        try
        {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null)
                throw  new UsernameNotFoundException("User with username: " + username + " not found");

            String token = jwtTokenProvider.createToken(username, user.getRole());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e)
        {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}