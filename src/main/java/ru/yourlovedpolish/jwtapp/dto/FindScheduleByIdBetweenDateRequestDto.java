package ru.yourlovedpolish.jwtapp.dto;

import lombok.Data;

import java.util.Date;
@Data
public class FindScheduleByIdBetweenDateRequestDto {
    Long id;
    Date start;
    Date end;
}
