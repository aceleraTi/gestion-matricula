package com.acelerati.gestionmatricula.domain.util;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;

import java.util.Objects;

public class Validaciones {

    public static final String USUARIO_NO_AUTORIZADO = "Usuario no autorizado.";

    public static Usuario validarLogged(Long usuarioAut, Usuario usuario) {

        if (Objects.equals(usuarioAut, usuario.getTipoUsuario())) {
            return usuario;
        }
        throw new NotLoggedInException(USUARIO_NO_AUTORIZADO);


    }

    public static Profesor validarProfesor(Usuario usuario) {
        if (usuario.getTipoUsuario() == 3) {
            Profesor profesor = new Profesor();
            profesor.setId(usuario.getUsuarioId()
            );
            profesor.setNombre(usuario.getNombre());
            return profesor;
        }
        throw new NotLoggedInException(USUARIO_NO_AUTORIZADO);
    }

    public static Estudiante validarEstudiante(Usuario usuario) {
        if (usuario.getTipoUsuario() == 4) {
            Estudiante estudiante = new Estudiante();
            estudiante.setId(usuario.getUsuarioId());
            estudiante.setNombre(usuario.getNombre());
            return estudiante;
        }
        throw new NotLoggedInException(USUARIO_NO_AUTORIZADO);
    }

}
