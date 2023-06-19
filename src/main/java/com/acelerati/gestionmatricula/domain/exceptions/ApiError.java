package com.acelerati.gestionmatricula.domain.exceptions;



import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


@Schema(name = "ApiError",description = "Modelo de respuesta para errores")
public class ApiError {
    @Schema(name = "status",description = "Codigo http status de respuesta")
    private int status;
    @Schema(name = "message",description = "Motivo por el que se lanza la excepcion")
    private String message;
    @Schema(name = "errors",description = "Lista de errores")
    private List<String> errors;


    public ApiError(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}