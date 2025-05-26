package com.senai.classline.exceptions.professor;

public class ProfessorNotFound extends RuntimeException{

    public ProfessorNotFound(){super("Professor não encontrado");}

    public ProfessorNotFound(String message) {super(message);}

}