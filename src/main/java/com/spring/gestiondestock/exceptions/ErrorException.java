package com.spring.gestiondestock.exceptions;

public class ErrorException extends RuntimeException{
    public ErrorException(String message) {
        super(message);
    }
}
