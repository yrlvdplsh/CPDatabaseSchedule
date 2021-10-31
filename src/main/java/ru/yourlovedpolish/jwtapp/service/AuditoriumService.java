package ru.yourlovedpolish.jwtapp.service;

import ru.yourlovedpolish.jwtapp.model.Auditorium;
import ru.yourlovedpolish.jwtapp.model.Tutor;

import java.util.List;
import java.util.Optional;

public interface AuditoriumService{
    Optional<Auditorium> findById(Long id);
    List<Auditorium> getAll();
    void deleteById(Long id);
    void add(Auditorium auditorium);
}
