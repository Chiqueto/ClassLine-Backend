package com.senai.classline.exceptions.professor;

public class ProfessorChangeUnauthorized extends RuntimeException{

    public ProfessorChangeUnauthorized(){super("Você não tem acesso para alterar esse professor!");}

    public ProfessorChangeUnauthorized(String message) {super(message);}

}