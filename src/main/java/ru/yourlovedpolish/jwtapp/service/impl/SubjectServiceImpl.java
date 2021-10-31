package ru.yourlovedpolish.jwtapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yourlovedpolish.jwtapp.model.Subject;
import ru.yourlovedpolish.jwtapp.repository.SubjectRepository;
import ru.yourlovedpolish.jwtapp.service.SubjectService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    public List<Subject> getAll() {
        List<Subject> result = subjectRepository.findAll();
        log.info("IN getAll - {} groups found", result.size());
        return result;
    }



    @Override
    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public void add(Subject subject) {
        subjectRepository.save(subject);
    }

    @Override
    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }
}
