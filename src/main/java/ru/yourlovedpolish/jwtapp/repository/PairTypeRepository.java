package ru.yourlovedpolish.jwtapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yourlovedpolish.jwtapp.model.PairType;

import java.util.Optional;

public interface PairTypeRepository extends JpaRepository<PairType, Long> {
    Optional<PairType> findById(Long id);
}
