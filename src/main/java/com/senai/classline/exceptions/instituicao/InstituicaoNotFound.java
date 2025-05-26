package com.senai.classline.exceptions.instituicao;


public class InstituicaoNotFound extends RuntimeException {
    public InstituicaoNotFound(){super("Instituição não encontrada!");}

    public InstituicaoNotFound(String message) {super(message);}
}
