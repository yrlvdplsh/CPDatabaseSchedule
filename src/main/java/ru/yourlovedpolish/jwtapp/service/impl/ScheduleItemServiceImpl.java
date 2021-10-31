package ru.yourlovedpolish.jwtapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yourlovedpolish.jwtapp.model.ScheduleItem;
import ru.yourlovedpolish.jwtapp.repository.ScheduleItemRepository;
import ru.yourlovedpolish.jwtapp.service.ScheduleItemService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleItemServiceImpl implements ScheduleItemService {
    public final ScheduleItemRepository scheduleItemRepository;

    @Autowired
    public ScheduleItemServiceImpl(ScheduleItemRepository scheduleItemRepository) {
        this.scheduleItemRepository = scheduleItemRepository;
    }

    @Override
    public Optional<ScheduleItem> findById(Long id) {
        return scheduleItemRepository.findById(id);
    }

    @Override
    public List<ScheduleItem> findByIdGroupBetweenDate(Long id, Date start, Date end) {
        return scheduleItemRepository.findByGroupIdAndDateBetween(id, start, end);
    }

    @Override
    public void add(ScheduleItem item) {
        scheduleItemRepository.save(item);
    }

    @Override
    public void deleteById(Long scheduleId) {
        scheduleItemRepository.deleteById(scheduleId);
    }


}
