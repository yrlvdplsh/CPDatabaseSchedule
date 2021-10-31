package ru.yourlovedpolish.jwtapp.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tutors")
@Data
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String name;
}
