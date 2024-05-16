package br.com.rest.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionHandlerDTO> handleEntityNotFoundException(){
        var response = new ExceptionHandlerDTO("Não encontrado: alguma das informações está incorreta.");
        return ResponseEntity.badRequest().body(response); // retornar response.getMessage() se o tipo do retorno for String
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionHandlerDTO> handleRuntimeException(){
        var response = new ExceptionHandlerDTO("Ocorreu um erro interno no servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
