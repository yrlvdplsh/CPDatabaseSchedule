package ru.yourlovedpolish.jwtapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yourlovedpolish.jwtapp.model.Tutor;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findById(Long id);
    void deleteById(Long id);
}
