package com.senai.classline.dto.professor;

import com.senai.classline.enums.Formacao;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.Turno;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

public record ProfessorDTO(
        String id_instituicao,
        @NotNull(message = "Nome não pode ser nulo")
        @NotEmpty(message = "Nome não pode estar vazio")
        String nome,
        @NotNull(message = "Email não pode ser nulo")
        @NotEmpty(message = "Email não pode estar vazio")
        @Email(message = "Email deve estar no formato correto")
        String email,
        @NotEmpty(message = "Senha não pode estar vazio")
        @NotNull(message = "Senha não pode ser nula")
        String senha,
        @NotEmpty(message = "CPF não pode estar vazio")
        @NotNull(message = "CPF não pode ser nulo")
        @CPF(message = "CPF deve estar no formato correto")
        String cpf,
        @NotNull(message = "Data de nascimento não pode ser nula")
        Date dt_nascimento,
        @NotEmpty(message = "Genero não pode estar vazio")
        @NotNull(message = "Genero não pode ser nulo")
        String genero,
        String logradouro,
        String bairro,
        String numero,
        String cidade,
        @Pattern(regexp = "^\\(?(\\d{2})\\)?[- ]?(\\d{4,5})[- ]?(\\d{4})$",
                message = "Telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX")
        String telefone,
        Turno turno,

        StatusPessoa status,
        Formacao formacao,
        @NotEmpty(message = "Area de atuação não pode estar vazio")
        @NotNull(message = "Area de atuação não pode ser nulo")
        String area_atuacao,
        String diploma,
        @NotNull(message = "Data de admissão não pode ser nulo")
        Date dt_admissao) {
}
