package com.senai.classline.infra;

import com.senai.classline.exceptions.curso.CursoAlreadyExists;
import com.senai.classline.exceptions.curso.CursoChangeUnauthorized;
import com.senai.classline.exceptions.curso.CursoNotFound;
import com.senai.classline.exceptions.instituicao.InstituicaoAlreadyExists;
import com.senai.classline.exceptions.global.LoginFail;
import com.senai.classline.exceptions.instituicao.InstituicaoNotFound;
import com.senai.classline.exceptions.professor.ProfessorAlreadyExists;
import com.senai.classline.exceptions.professor.ProfessorChangeUnauthorized;
import com.senai.classline.exceptions.professor.ProfessorNotFound;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

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
        String errorMessage = (exception != null && exception.getMessage() != null)
                ? exception.getMessage()
                : "Professor já cadastrada com esse CPF!";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(CursoAlreadyExists.class)
    private ResponseEntity<String> cursoAlreadyExists (CursoAlreadyExists exception){
        String errorMessage = (exception != null && exception.getMessage() != null)
                ? exception.getMessage()
                : "Curso com esse nome já cadastrado";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(CursoNotFound.class)
    private ResponseEntity<String> cursoAlreadyExists (CursoNotFound exception){
        String errorMessage = (exception != null && exception.getMessage() != null)
                ? exception.getMessage()
                : "Curso não encontrado!";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(ProfessorNotFound.class)
    private ResponseEntity<String> professorNotFound (ProfessorNotFound exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado!");
    }

    @ExceptionHandler(ProfessorChangeUnauthorized.class)
    private ResponseEntity<String> professorChangeUnauthorized (ProfessorChangeUnauthorized exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não tem permissão para alterar esse professor!");
    }

    @ExceptionHandler(CursoChangeUnauthorized.class)
    private ResponseEntity<String> cursoChangeUnauthorized (CursoChangeUnauthorized exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não tem permissão para alterar esse curso!");
    }

    @ExceptionHandler(InstituicaoNotFound.class)
    private ResponseEntity<String> InstituicaoNotFound (InstituicaoNotFound exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instituição não encontrada!");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}