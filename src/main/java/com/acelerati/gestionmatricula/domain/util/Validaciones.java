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
            if(usuarioAut.equalsIgnoreCase(usuario.getTipoUsuario())){
                return usuario;
            }
                throw new NotLoggedInException("Usuario no autorizado");

       } catch (NullPointerException e) {
            throw new NotLoggedInException();
        }

    }

    public static Profesor validarProfesor(Usuario usuario){
        if(usuario.getTipoUsuario().equalsIgnoreCase("profesor")){
            Profesor profesor = new Profesor();
            profesor.setId(usuario.getId());
            profesor.setNombre(usuario.getNombre());
            return profesor;
        }
        throw new NotLoggedInException("Usuario no autorizado");
    }

    public static Estudiante validarEstudiante(Usuario usuario){
        if(usuario.getTipoUsuario().equalsIgnoreCase("estudiante")){
            Estudiante estudiante=new Estudiante();
            estudiante.setId(usuario.getId());
            estudiante.setNombre(usuario.getNombre());

            return estudiante;
        }
        throw new NotLoggedInException("Usuario no autorizado");
    }

}
