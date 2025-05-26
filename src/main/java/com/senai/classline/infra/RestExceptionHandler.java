package com.senai.classline.infra;

import com.senai.classline.exceptions.instituicao.InstituicaoAlreadyExists;
import com.senai.classline.exceptions.instituicao.InstituicaoLoginFail;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Hidden
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InstituicaoAlreadyExists.class)
    private ResponseEntity<String> instituicaoAlreadyExistsHandle (InstituicaoAlreadyExists exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Instituição já cadastrada com esse e-mail!");
    }

    @ExceptionHandler(InstituicaoLoginFail.class)
    private ResponseEntity<String> instituicaoLoginFailHandle (InstituicaoLoginFail exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas!");
    }
}
