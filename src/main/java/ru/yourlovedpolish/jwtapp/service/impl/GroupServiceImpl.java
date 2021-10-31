package ru.yourlovedpolish.jwtapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yourlovedpolish.jwtapp.model.Group;
import ru.yourlovedpolish.jwtapp.repository.GroupsRepository;
import ru.yourlovedpolish.jwtapp.service.GroupService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class GroupServiceImpl implements GroupService {
    private final GroupsRepository groupsRepository;

    @Autowired
    public GroupServiceImpl(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupsRepository.findById(id);
    }

    @Override
    public List<Group> getAll() {
        List<Group> result = groupsRepository.findAll();
        log.info("IN getAll - {} groups found", result.size());
        return result;
    }

    @Override
    public void deleteById(Long id) {
        groupsRepository.deleteById(id);
    }

    public void findByName(String name){groupsRepository.findByName(name);}

    @Override
    public void add(Group group) {
        groupsRepository.save(group);
    }
}
