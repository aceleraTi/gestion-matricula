package com.acelerati.gestionmatricula.domain.model;


import javax.persistence.Transient;



public class Pensum {

    private Long id;
    @Transient
    private Integer anio;


    public Pensum(Long id, Integer anio) {
        this.id = id;
        this.anio = anio;
    }

    public Pensum() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
}
