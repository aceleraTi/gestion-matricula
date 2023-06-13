package com.acelerati.gestionmatricula.infraestructure.exceptions;

public class NotCreatedInException extends RuntimeException {


    public NotCreatedInException(){
        super("Fallo en la transaccion");
    }

    public NotCreatedInException(String message){
        super(message);
    }
}


