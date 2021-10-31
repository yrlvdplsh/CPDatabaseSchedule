package ru.yourlovedpolish.jwtapp.service;

import ru.yourlovedpolish.jwtapp.model.PairType;
import ru.yourlovedpolish.jwtapp.model.ScheduleItem;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleItemService {
    Optional<ScheduleItem> findById(Long id);
    List<ScheduleItem> findByIdGroupBetweenDate(Long id, Date start, Date end);
    void add(ScheduleItem item);

    void deleteById(Long scheduleId);
}
