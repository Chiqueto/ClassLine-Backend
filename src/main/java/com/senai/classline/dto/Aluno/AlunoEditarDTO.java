package com.senai.classline.dto.Aluno;

import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.Turno;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

public record AlunoEditarDTO (

        String nome,

        @Email(message = "Email deve estar no formato correto")
        String email,

        String senha,

        @CPF(message = "CPF deve estar no formato correto")
        String cpf,

        Date dt_nascimento,

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

        Long id_turma,

        Long id_curso,
        Date dt_inicio,
        Date dt_fim
){
}
