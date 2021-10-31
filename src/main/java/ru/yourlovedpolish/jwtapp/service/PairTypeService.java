package ru.yourlovedpolish.jwtapp.service;

import ru.yourlovedpolish.jwtapp.model.PairType;

import java.util.List;
import java.util.Optional;

public interface PairTypeService {
    List<PairType> getAll();
    Optional<PairType> findById(Long id);
}
