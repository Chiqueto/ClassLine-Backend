package com.senai.classline.dto.professor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.classline.enums.Formacao;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.Turno;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProfessorEditarDTO (
        String email,
        String senha,
        String genero,
        String logradouro,
        String bairro,
        String numero,
        String cidade,
        String telefone,
        Turno turno,
        Formacao formacao,
        String area_atuacao,
        Date dt_admissao)
{}
