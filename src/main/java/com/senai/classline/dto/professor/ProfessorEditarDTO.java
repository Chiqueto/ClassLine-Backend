package com.senai.classline.dto.professor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.classline.enums.Formacao;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.Turno;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProfessorEditarDTO (
        @Email(message = "Email deve estar no formato correto")
        String email,
        @Min(value = 3, message = "Mínimo de 3 caracteres para senha!")
        String senha,
        String genero,
        String logradouro,
        String bairro,
        String numero,
        String cidade,
        @Pattern(regexp = "^\\(?(\\d{2})\\)?[- ]?(\\d{4,5})[- ]?(\\d{4})$",
                message = "Telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX")
        String telefone,
        Turno turno,
        Formacao formacao,
        String area_atuacao,
        Date dt_admissao)
{}
