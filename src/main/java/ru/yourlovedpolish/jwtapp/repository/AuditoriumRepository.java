package ru.yourlovedpolish.jwtapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yourlovedpolish.jwtapp.model.Auditorium;


import java.util.Optional;

public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    Optional<Auditorium> findById(Long id);
    void deleteById(Long id);
}
