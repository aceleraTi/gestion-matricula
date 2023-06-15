package com.acelerati.gestionmatricula.application.service;

import com.acelerati.gestionmatricula.application.service.interfaces.CursoService;
import com.acelerati.gestionmatricula.domain.model.Curso;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.infraestructure.driven_adapters.persistence.interfaces.jpa_repository.CursoRepository;
import com.acelerati.gestionmatricula.infraestructure.entitys.CursoEntity;
import com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCurso;
import static com.acelerati.gestionmatricula.infraestructure.rest.mappers.CursoMapper.alCursoEntity;

@Service
public class CursoServiceDefault implements CursoService {

    private final CursoRepository cursoRepository;

    public CursoServiceDefault(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public Curso create(Curso curso) {
        CursoEntity cursoEntity=alCursoEntity(curso);
        return alCurso(cursoRepository.save(cursoEntity)) ;
    }
    @Override
    public Curso update(Curso curso) {
        CursoEntity cursoEntity=alCursoEntity(curso);
        return alCurso(cursoRepository.update(cursoEntity)) ;
    }

    @Override
    public Page<Curso> findByProfesor(Profesor profesor, Pageable pageable) {

        Page<CursoEntity> cursoEntityPage=cursoRepository.findByProfesor(profesor,pageable);
        List<Curso> cursoList=cursoEntityPage.getContent()
                .stream()
                .map(CursoMapper::alCurso)
                .collect(Collectors.toList());
        return new PageImpl<>(cursoList,cursoEntityPage.getPageable(),cursoEntityPage.getTotalElements());
    }

    @Override
    public Curso findById(Long id) {
        return alCurso(cursoRepository.findById(id));
    }




}
