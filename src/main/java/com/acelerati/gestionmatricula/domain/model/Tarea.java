package com.acelerati.gestionmatricula.domain.model;

public class Tarea {
    private Long id;
    private Curso curso;
    private String descripcion;


    public Tarea() {
    }

    public Tarea(Long id, Curso curso, String descripcion) {
        this.id = id;
        this.curso = curso;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
