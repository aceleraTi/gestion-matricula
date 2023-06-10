package com.acelerati.gestionmatricula.infraestructure.entitys;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@Data

@Entity
@Table(name = "semestres_academicos")
public class SemestreAcademicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    private Integer numero;
    @Min(2023)
    private Integer año;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;

    //__________________________________Relaciones____________________________

    @OneToMany(mappedBy = "semestreAcademico",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CursoEntity> cursos;


}
