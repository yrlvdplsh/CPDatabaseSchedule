package ru.yourlovedpolish.jwtapp.dto;

import lombok.Data;

import java.util.Date;
@Data
public class AddScheduleItemDto {
    Long groupId;
    Long subjectId;
    Long tutorId;
    Long auditoriumId;
    Long pairTypeId;
    Date date;
}
