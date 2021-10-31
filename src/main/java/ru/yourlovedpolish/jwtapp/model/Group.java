package ru.yourlovedpolish.jwtapp.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "groups")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
