package com.acelerati.gestionmatricula.domain.model;


import javax.persistence.Transient;



public class Profesor {
    private Long id;
    @Transient
    private String nombre;
    @Transient
    private String apellido;
    @Transient
    private String email;
    @Transient
    private String codigo;


    public Profesor() {
    }

    public Profesor(Long id, String nombre, String apellido, String email, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.codigo = codigo;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
