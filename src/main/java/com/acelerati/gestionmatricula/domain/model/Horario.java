package com.acelerati.gestionmatricula.domain.model;

import java.time.LocalTime;


public class Horario {

    private Long id;
    private Curso curso;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String dia;
    private String link;


    public Horario() {
    }

    public Horario(Long id, Curso curso, LocalTime horaInicio, LocalTime horaFin, String dia, String link) {
        this.id = id;
        this.curso = curso;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.dia = dia;
        this.link = link;
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

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
