package ru.yourlovedpolish.jwtapp.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.yourlovedpolish.jwtapp.model.Role;
import ru.yourlovedpolish.jwtapp.security.JwtUserDetailsService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider
{
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    public String createToken(String login, Role role)
    {
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("role", getRoleNames(role));

        Date now = new Date();
        Date validaty = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validaty)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @PostConstruct
    protected void init()
    {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token)
    {
        UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    String getUsername(String token)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_"))
            return bearerToken.substring(7, bearerToken.length());
        return null;
    }

    public boolean validateToken(String token) throws JwtAuthenticationException
    {
        try
        {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date()))
                return false;
            return true;

        } catch (JwtException | IllegalArgumentException e)
        {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    private String getRoleNames(Role userRole)
    {
        return userRole.getName();
    }
}
