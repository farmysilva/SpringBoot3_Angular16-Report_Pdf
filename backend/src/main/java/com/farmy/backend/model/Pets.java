package com.farmy.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pets_table")
@Data

public class Pets {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //Nome Tipo raca donos
    private String nome;

    private String tipo;

    private String raca;

    private String donos;
    
}
