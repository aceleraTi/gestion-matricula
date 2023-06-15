package com.acelerati.gestionmatricula.domain.model;


import javax.persistence.Transient;




public class Estudiante {
    private Long id;
    @Transient
    private String nombre;
    @Transient
    private String apellido;
    @Transient
    private String email;
    @Transient
    private String codigo;


    public Estudiante() {
    }

    public Estudiante(Long id, String nombre, String apellido, String email, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.codigo = codigo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
