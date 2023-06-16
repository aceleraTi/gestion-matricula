package com.acelerati.gestionmatricula.domain.model;

import java.util.Date;


public class SemestreAcademico  {

    private Long id;
    private Integer numero;
    private Integer año;
    private Date fechaInicio;
    private Date fechaFin;

    public SemestreAcademico() {
    }

    public SemestreAcademico(Long id, Integer numero, Integer año, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.numero = numero;
        this.año = año;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
