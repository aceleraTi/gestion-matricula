package com.acelerati.gestionmatricula.infraestructure.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class ApiError {
    private int status;
    private String message;
    private List<String> errors;



    public ApiError(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }


}