package com.acelerati.gestionmatricula.domain.util;

import com.acelerati.gestionmatricula.domain.model.Estudiante;
import com.acelerati.gestionmatricula.domain.model.Profesor;
import com.acelerati.gestionmatricula.domain.model.Usuario;
import com.acelerati.gestionmatricula.infraestructure.exceptions.NotLoggedInException;

import javax.servlet.http.HttpSession;

public class Validaciones {

    public static Usuario validarLogged(String usuarioAut,HttpSession session){
        try {
            Usuario usuario=(Usuario) session.getAttribute("usuario");
            System.out.println(usuario.getTipoUsuario());
            System.out.println("--"+usuarioAut);
            if(usuarioAut.equals(usuario.getTipoUsuario())){
                return usuario;
            }
                throw new NotLoggedInException("Usuario no autorizado");

       } catch (NullPointerException e) {
            throw new NotLoggedInException();
        }

    }

    public static Profesor validarProfesor(Usuario usuario){
        if(usuario.getTipoUsuario().equals("profesor")){
           return Profesor.builder()
                    .id(usuario.getId())
                    .nombre(usuario.getNombre()).build();
        }
        throw new NotLoggedInException("Usuario no autorizado");
    }

    public static Estudiante validarAlumno(Usuario usuario){
        if(usuario.getTipoUsuario().equals("Estudiante")){
            return Estudiante
                    .builder()
                    .id(usuario.getId())
                    .nombre(usuario.getNombre()).build();
        }
        throw new NotLoggedInException("Usuario no autorizado");
    }

}
