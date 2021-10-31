package ru.yourlovedpolish.jwtapp.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yourlovedpolish.jwtapp.model.PairType;
import ru.yourlovedpolish.jwtapp.repository.PairTypeRepository;
import ru.yourlovedpolish.jwtapp.service.PairTypeService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class PairTypeServiceImpl implements PairTypeService {
    private final PairTypeRepository pairTypeRepository;

    public PairTypeServiceImpl(PairTypeRepository pairTypeRepository) {
        this.pairTypeRepository = pairTypeRepository;
    }

    @Override
    public List<PairType> getAll() {
        return pairTypeRepository.findAll();
    }

    @Override
    public Optional<PairType> findById(Long id) {
        return pairTypeRepository.findById(id);
    }
}
