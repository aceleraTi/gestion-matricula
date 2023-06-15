package com.acelerati.gestionmatricula.domain.model;


import javax.persistence.Transient;



public class Pensum {

    private Long id;
    @Transient
    private Integer año;


    public Pensum(Long id, Integer año) {
        this.id = id;
        this.año = año;
    }

    public Pensum() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }
}
