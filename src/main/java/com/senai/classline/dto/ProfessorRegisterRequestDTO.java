package com.senai.classline.dto;

import com.senai.classline.enums.Formacao;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.Turno;

import java.util.Date;

public record ProfessorRegisterRequestDTO(String id_instituicao, String nome, String email, String senha, String cpf, Date dt_nascimento,
                                          String genero, String logradouro, String bairro, String numero, String cidade, String telefone,
                                          Turno turno, StatusPessoa status, Formacao formacao, String area_atuacao, String diploma, Date dt_admissao) {
}
