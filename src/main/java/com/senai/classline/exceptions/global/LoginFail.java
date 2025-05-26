package com.senai.classline.exceptions.global;

public class LoginFail extends RuntimeException{
    public LoginFail(){super("Usuário ou senha incorreta!");}

    public LoginFail(String message) {super(message);}
}
