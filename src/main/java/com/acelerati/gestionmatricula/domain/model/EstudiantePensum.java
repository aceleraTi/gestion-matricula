package com.acelerati.gestionmatricula.domain.model;

public class EstudiantePensum {
    private Long id;
    private Estudiante estudiante;
    private Pensum pensum;

    public EstudiantePensum() {
    }

    public EstudiantePensum(Long id, Estudiante estudiante, Pensum pensum) {
        this.id = id;
        this.estudiante = estudiante;
        this.pensum = pensum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Pensum getPensum() {
        return pensum;
    }

    public void setPensum(Pensum pensum) {
        this.pensum = pensum;
    }
}
