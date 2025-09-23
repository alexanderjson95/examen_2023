package com.example.backend.Exceptions;

public class NotAdminException extends  RuntimeException{
    public NotAdminException(String message){
        super(message);
    }
}
