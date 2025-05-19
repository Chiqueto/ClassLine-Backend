package com.senai.classline.dto;

public record InstituicaoRegisterRequestDTO(String nome, String email, String senha,
		String logradouro, String bairro, String numero, String cidade, String telefone) {

}
