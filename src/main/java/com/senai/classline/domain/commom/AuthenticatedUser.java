package com.senai.classline.domain.commom;

public interface AuthenticatedUser {
    String getLoginIdentifier(); // pode ser CPF ou Email
    String getSenha();
}