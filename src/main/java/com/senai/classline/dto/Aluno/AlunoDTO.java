package com.senai.classline.dto.Aluno;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.enums.Formacao;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.Turno;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

public record AlunoDTO(
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
        @NotNull(message = "Turno não pode ser nulo")
        Turno turno,
        StatusPessoa status,
        @NotNull(message = "Turma não pode ser nula")
        Long id_turma,
        @NotNull(message = "Curso não pode ser nulo")
        Long id_curso,
        @NotNull(message = "Data de início não pode ser nula")
        Date dt_inicio,
        Date dt_fim
) {

}
