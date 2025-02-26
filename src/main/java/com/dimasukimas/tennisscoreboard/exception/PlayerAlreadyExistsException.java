package com.dimasukimas.tennisscoreboard.exception;

public class PlayerAlreadyExistsException extends RuntimeException{

    public PlayerAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
