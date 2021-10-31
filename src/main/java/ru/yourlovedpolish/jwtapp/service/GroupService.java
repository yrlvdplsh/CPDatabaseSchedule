package ru.yourlovedpolish.jwtapp.service;

import ru.yourlovedpolish.jwtapp.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    Optional<Group> findById(Long id);
    List<Group> getAll();
    void deleteById(Long id);
    void add(Group group);
}
