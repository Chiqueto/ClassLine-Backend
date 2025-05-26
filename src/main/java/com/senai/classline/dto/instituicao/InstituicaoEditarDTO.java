package com.senai.classline.dto.instituicao;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InstituicaoEditarDTO(
        String nome,
        String email,
        String senha,
        String logradouro,
        String bairro,
        String numero,
        String cidade,
        String telefone
) {}
