package com.senai.classline.exceptions.instituicao;

public class InstituicaoLoginFail extends RuntimeException{
    public InstituicaoLoginFail(){super("Usuário ou senha incorreta!");}

    public InstituicaoLoginFail(String message) {super(message);}
}
