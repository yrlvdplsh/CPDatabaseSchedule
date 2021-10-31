package ru.yourlovedpolish.jwtapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yourlovedpolish.jwtapp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findByName(String name);

}