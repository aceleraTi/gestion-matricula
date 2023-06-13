package com.acelerati.gestionmatricula.infraestructure.exceptions;

public class NotLoggedInException extends RuntimeException {

    public NotLoggedInException() {
        super("Usuario no logueado");
    }

    public NotLoggedInException(String message) {
        super(message);
    }



}