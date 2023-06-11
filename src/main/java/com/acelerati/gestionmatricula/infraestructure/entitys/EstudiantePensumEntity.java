package com.acelerati.gestionmatricula.infraestructure.entitys;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "estudiantes_pensums")
public class EstudiantePensumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  Long idEstudiante;
    private Long idPensum;

}
