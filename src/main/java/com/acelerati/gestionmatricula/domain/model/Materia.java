package com.acelerati.gestionmatricula.domain.model;


import javax.persistence.Transient;
import javax.validation.constraints.NotNull;



public class Materia {
    @NotNull
    private Long id;
    @NotNull
    @Transient
    private Pensum pensum;
    @Transient
    private String nombre;
    @Transient
    private String descripcion;
    @Transient
    private Materia materiaPrerequisito;

    public Materia() {
    }

    public Materia(Long id, Pensum pensum, String nombre, String descripcion, Materia materiaPrerequisito) {
        this.id = id;
        this.pensum = pensum;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.materiaPrerequisito = materiaPrerequisito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pensum getPensum() {
        return pensum;
    }

    public void setPensum(Pensum pensum) {
        this.pensum = pensum;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Materia getMateriaPrerequisito() {
        return materiaPrerequisito;
    }

    public void setMateriaPrerequisito(Materia materiaPrerequisito) {
        this.materiaPrerequisito = materiaPrerequisito;
    }
}
