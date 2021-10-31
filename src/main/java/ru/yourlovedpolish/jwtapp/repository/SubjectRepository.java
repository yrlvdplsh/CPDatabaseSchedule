package ru.yourlovedpolish.jwtapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yourlovedpolish.jwtapp.model.ScheduleItem;
import ru.yourlovedpolish.jwtapp.model.Subject;
import ru.yourlovedpolish.jwtapp.model.Tutor;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findById(Long id);
    void deleteById(Long id);
}
