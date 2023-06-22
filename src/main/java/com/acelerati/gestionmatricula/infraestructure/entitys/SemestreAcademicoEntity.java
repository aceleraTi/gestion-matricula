package com.acelerati.gestionmatricula.infraestructure.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "semestres_academicos")
public class SemestreAcademicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    private Integer numero;
    @Min(2023)
    @Column(name = "anio")
    private Integer año;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;

    //__________________________________Relaciones____________________________

    @OneToMany(mappedBy = "semestreAcademicoEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CursoEntity> cursos;


}
