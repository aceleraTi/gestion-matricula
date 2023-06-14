package com.acelerati.gestionmatricula.infraestructure.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<ApiError> handleNotLoggedInException(NotLoggedInException ex) {

        List<String> errors=new ArrayList<>();
        errors.add(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }


    @ExceptionHandler(NotCreatedInException.class)
    public ResponseEntity<ApiError> handleNotCreatedInException(NotCreatedInException ex){
        List<String> errors=new ArrayList<>();
        errors.add(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(NotFoundItemsInException.class)
    public ResponseEntity<ApiError> handleNotFoundItemsInException(NotFoundItemsInException ex){
        List<String> errors=new ArrayList<>();
        errors.add(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(NotApplicationProcessed.class)
    public ResponseEntity<ApiError> handleNotApplicationProcessedInException(NotApplicationProcessed ex){
        List<String> errors=new ArrayList<>();
        errors.add(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


}
