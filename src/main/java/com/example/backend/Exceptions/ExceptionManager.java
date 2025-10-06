package com.example.backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionManager {


    @ExceptionHandler(NotAdminException.class)
    public ResponseEntity<StatusResponse> handleNotAdmin(NotAdminException e) {
        StatusResponse status = new StatusResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return new ResponseEntity<>(status, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StatusResponse> handleNotFound(ResourceNotFoundException e){
        StatusResponse status = new StatusResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(status,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwTAuthenticationException.class)
    public ResponseEntity<StatusResponse> handleJwTAuthException(JwTAuthenticationException e){
        StatusResponse status = new StatusResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return new ResponseEntity<>(status,HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(DataTakenException.class)
    public ResponseEntity<StatusResponse> handleDataTakenException(DataTakenException e){
        StatusResponse status = new StatusResponse(HttpStatus.CONFLICT.value(), e.getMessage());
        return new ResponseEntity<>(status,HttpStatus.CONFLICT);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException e){
        Map<String,String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach( er ->
                errorMap.put(er.getField(), er.getDefaultMessage()));
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }





}
