package br.com.rest.exception;

import lombok.Getter;

@Getter
public class ExceptionHandlerDTO {
    String message;

    public ExceptionHandlerDTO(String message){
        this.message = message;
    }
}
