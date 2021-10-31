package ru.yourlovedpolish.jwtapp.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "auditoriums")
@Data
public class Auditorium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "address")
    private String address;
}
