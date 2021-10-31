package ru.yourlovedpolish.jwtapp.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
}
