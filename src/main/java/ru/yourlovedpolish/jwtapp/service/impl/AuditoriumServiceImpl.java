package ru.yourlovedpolish.jwtapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yourlovedpolish.jwtapp.model.Auditorium;
import ru.yourlovedpolish.jwtapp.repository.AuditoriumRepository;
import ru.yourlovedpolish.jwtapp.service.AuditoriumService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class AuditoriumServiceImpl implements AuditoriumService {
    private final AuditoriumRepository auditoriumRepository;

    @Autowired
    public AuditoriumServiceImpl(AuditoriumRepository auditoriumRepository) {
        this.auditoriumRepository = auditoriumRepository;
    }



    public List<Auditorium> getAll() {
        List<Auditorium> result = auditoriumRepository.findAll();
        log.info("IN getAll - {} groups found", result.size());
        return result;
    }

    @Override
    public void deleteById(Long id) {
        auditoriumRepository.deleteById(id);
    }

    @Override
    public void add(Auditorium auditorium) {
        auditoriumRepository.save(auditorium);
    }

    @Override
    public Optional<Auditorium> findById(Long id) {
        return auditoriumRepository.findById(id);
    }
}
