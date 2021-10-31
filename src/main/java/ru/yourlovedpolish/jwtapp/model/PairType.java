package ru.yourlovedpolish.jwtapp.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "pair_type")
@Data
public class PairType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private String type;
}
