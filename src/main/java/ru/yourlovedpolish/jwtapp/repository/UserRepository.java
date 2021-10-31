package ru.yourlovedpolish.jwtapp.repository;

import ru.yourlovedpolish.jwtapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String name);
}
