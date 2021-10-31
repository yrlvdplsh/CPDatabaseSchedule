package ru.yourlovedpolish.jwtapp.service;

import ru.yourlovedpolish.jwtapp.model.Group;
import ru.yourlovedpolish.jwtapp.model.Subject;
import ru.yourlovedpolish.jwtapp.model.Tutor;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    Optional<Subject> findById(Long id);
    List<Subject> getAll();
    void deleteById(Long id);

    void add(Subject subject);
}
