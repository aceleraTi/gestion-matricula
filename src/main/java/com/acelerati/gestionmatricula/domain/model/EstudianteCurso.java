package com.acelerati.gestionmatricula.domain.model;

public class EstudianteCurso {

    private Long id;
    private Estudiante estudiante;
    private Curso curso;
    private Double previo1;
    private Double previo2;
    private Double previo3;
    private Double previo4;
    private Double notaFinal;


    public EstudianteCurso() {
    }

    public EstudianteCurso(Long id, Estudiante estudiante, Curso curso,
                           Double previo1, Double previo2, Double previo3, Double previo4, Double notaFinal) {
        this.id = id;
        this.estudiante = estudiante;
        this.curso = curso;
        this.previo1 = previo1;
        this.previo2 = previo2;
        this.previo3 = previo3;
        this.previo4 = previo4;
        this.notaFinal = notaFinal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Double getPrevio1() {
        return previo1;
    }

    public void setPrevio1(Double previo1) {
        this.previo1 = previo1;
    }

    public Double getPrevio2() {
        return previo2;
    }

    public void setPrevio2(Double previo2) {
        this.previo2 = previo2;
    }

    public Double getPrevio3() {
        return previo3;
    }

    public void setPrevio3(Double previo3) {
        this.previo3 = previo3;
    }

    public Double getPrevio4() {
        return previo4;
    }

    public void setPrevio4(Double previo4) {
        this.previo4 = previo4;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }
}
