package com.senai.classline.exceptions.instituicao;

public class InstituicaoAlreadyExists extends RuntimeException{
    public InstituicaoAlreadyExists(){super("Instituição já está cadastrada");}

    public InstituicaoAlreadyExists(String message) {super(message);}
}
