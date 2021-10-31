package ru.yourlovedpolish.jwtapp.service;

import ru.yourlovedpolish.jwtapp.model.Tutor;

import java.util.List;
import java.util.Optional;

public interface TutorService {
    Optional<Tutor> findById(Long id);
    List<Tutor> getAll();
    void deleteById(Long id);

    void add(Tutor tutor);
}
