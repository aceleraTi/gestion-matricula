package com.acelerati.gestionmatricula.domain.model;

public class Curso {

    private Long id;
    private Materia materia;
    private Profesor profesor;
    private SemestreAcademico semestreAcademico;
    private Integer grupo;
    private String estado;

    public Curso() {
    }

    public Curso(Long id, Materia materia, Profesor profesor, SemestreAcademico semestreAcademico, Integer grupo, String estado) {
        this.id = id;
        this.materia = materia;
        this.profesor = profesor;
        this.semestreAcademico = semestreAcademico;
        this.grupo = grupo;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public SemestreAcademico getSemestreAcademico() {
        return semestreAcademico;
    }

    public void setSemestreAcademico(SemestreAcademico semestreAcademico) {
        this.semestreAcademico = semestreAcademico;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
