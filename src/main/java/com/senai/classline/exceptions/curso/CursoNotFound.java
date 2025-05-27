package com.senai.classline.exceptions.curso;

public class CursoNotFound extends RuntimeException {
    public CursoNotFound(String message) {
        super(message);
    }
    public CursoNotFound() {
        super("Curso não encontrado!");
    }
}

