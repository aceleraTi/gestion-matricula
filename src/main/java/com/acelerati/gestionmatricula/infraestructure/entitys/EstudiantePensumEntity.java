package com.acelerati.gestionmatricula.infraestructure.entitys;


import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.Pensum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estudiantes_pensums")
public class EstudiantePensumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "id_estudiante"))
    private Estudiante estudiante;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "id_pensum"))
    private Pensum pensum;

}
