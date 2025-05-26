package com.senai.classline.infra;

import com.senai.classline.exceptions.instituicao.InstituicaoAlreadyExists;
import com.senai.classline.exceptions.global.LoginFail;
import com.senai.classline.exceptions.professor.ProfessorAlreadyExists;
import com.senai.classline.exceptions.professor.ProfessorChangeUnauthorized;
import com.senai.classline.exceptions.professor.ProfessorNotFound;
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

    @ExceptionHandler(LoginFail.class)
    private ResponseEntity<String> instituicaoLoginFailHandle (LoginFail exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas!");
    }

    @ExceptionHandler(ProfessorAlreadyExists.class)
    private ResponseEntity<String> professorAlreadyExistsHandle (ProfessorAlreadyExists exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Professor já cadastrada com esse CPF!");
    }

    @ExceptionHandler(ProfessorNotFound.class)
    private ResponseEntity<String> professorNotFound (ProfessorNotFound exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado!");
    }

    @ExceptionHandler(ProfessorChangeUnauthorized.class)
    private ResponseEntity<String> professorChangeUnauthorized (ProfessorChangeUnauthorized exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não tem permissão para alterar esse professor!");
    }


}
