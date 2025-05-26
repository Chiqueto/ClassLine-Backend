package com.senai.classline.exceptions.professor;

public class ProfessorAlreadyExists extends RuntimeException{

    public ProfessorAlreadyExists(){super("Professor já está cadastrado");}

    public ProfessorAlreadyExists(String message) {super(message);}

}
