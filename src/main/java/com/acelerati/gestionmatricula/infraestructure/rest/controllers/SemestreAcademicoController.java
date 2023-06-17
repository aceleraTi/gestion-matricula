package com.acelerati.gestionmatricula.infraestructure.rest.controllers;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.application.service.interfaces.SemestreAcademicoService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.SemestreAcademico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/semestreAc")
public class SemestreAcademicoController {

    @Autowired
    private SemestreAcademicoService semestreAcademicoService;

    @PostMapping("/created")
    public ResponseEntity<SemestreAcademico> crear(@RequestBody SemestreAcademico semestreAcademico){
        return new ResponseEntity<>(semestreAcademicoService.create(semestreAcademico), HttpStatus.CREATED);
    }
}
