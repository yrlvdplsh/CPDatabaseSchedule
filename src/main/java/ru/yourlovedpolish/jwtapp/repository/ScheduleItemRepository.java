package ru.yourlovedpolish.jwtapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yourlovedpolish.jwtapp.model.ScheduleItem;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {
    Optional<ScheduleItem> findById(Long id);
    List<ScheduleItem> findByGroupIdAndDateBetween(Long id, java.util.Date date, java.util.Date date2);
}
