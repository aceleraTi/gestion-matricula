package com.acelerati.gestionmatricula.domain.model;

public class EstudianteCursoTarea {
    private Long id;
    private EstudianteCurso estudianteCurso;
    private Tarea tarea;
    private Double nota;


    public EstudianteCursoTarea() {
    }

    public EstudianteCursoTarea(Long id, EstudianteCurso estudianteCurso, Tarea tarea, Double nota) {
        this.id = id;
        this.estudianteCurso = estudianteCurso;
        this.tarea = tarea;
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstudianteCurso getEstudianteCurso() {
        return estudianteCurso;
    }

    public void setEstudianteCurso(EstudianteCurso estudianteCurso) {
        this.estudianteCurso = estudianteCurso;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
