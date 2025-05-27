package com.senai.classline.exceptions.curso;

public class CursoAlreadyExists extends RuntimeException {
    public CursoAlreadyExists(String message) {
        super(message);
    }
    public CursoAlreadyExists(){super("Curso já está cadastrado com esse nome!");}
}
