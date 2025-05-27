package com.senai.classline.exceptions.curso;

public class CursoChangeUnauthorized extends RuntimeException {
  public CursoChangeUnauthorized(){super("Você não tem acesso para alterar esse curso!");}

    public CursoChangeUnauthorized(String message) {
        super(message);
    }
}
