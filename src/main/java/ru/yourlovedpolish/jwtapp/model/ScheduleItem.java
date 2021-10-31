package ru.yourlovedpolish.jwtapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "schedule_items")
@Data
public class ScheduleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    Date date;

    @ManyToOne
    @JoinColumn(name = "id_group")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "id_tutor")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "id_subject")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "id_pair_type")
    private PairType pairType;

    @ManyToOne
    @JoinColumn(name = "id_auditorium")
    private Auditorium auditorium;
}
