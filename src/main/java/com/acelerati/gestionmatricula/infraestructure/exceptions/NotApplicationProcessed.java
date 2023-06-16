package com.acelerati.gestionmatricula.infraestructure.exceptions;

public class NotApplicationProcessed extends RuntimeException{

    public NotApplicationProcessed(){
        super("Peticion no procesada");
    }

    public NotApplicationProcessed(String message){
        super(message);
    }
}
