package com.acelerati.gestionmatricula.infraestructure.exceptions;

public class NotFoundItemsInException extends RuntimeException{
    public NotFoundItemsInException(String message){
        super(message);
    }
}
