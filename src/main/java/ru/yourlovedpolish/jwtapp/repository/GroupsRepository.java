package ru.yourlovedpolish.jwtapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yourlovedpolish.jwtapp.model.Group;

import java.util.Optional;

public interface GroupsRepository extends JpaRepository<Group, Long> {
    Optional<Group> findById(Long id);
    @Override
    void deleteById(Long id);

    Optional<Group> findByName(String name);
}
