package ru.yourlovedpolish.jwtapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yourlovedpolish.jwtapp.model.Tutor;
import ru.yourlovedpolish.jwtapp.repository.TutorRepository;
import ru.yourlovedpolish.jwtapp.service.TutorService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class TutorServiceImpl implements TutorService {
    private final TutorRepository tutorRepository;

    @Autowired
    public TutorServiceImpl(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public List<Tutor> getAll() {
        List<Tutor> result = tutorRepository.findAll();
        log.info("IN getAll - {} groups found", result.size());
        return result;
    }

    @Override
    public void deleteById(Long id) {
        tutorRepository.deleteById(id);
    }

    @Override
    public void add(Tutor tutor) {
        tutorRepository.save(tutor);
    }

    @Override
    public Optional<Tutor> findById(Long id) {
        return tutorRepository.findById(id);
    }
}