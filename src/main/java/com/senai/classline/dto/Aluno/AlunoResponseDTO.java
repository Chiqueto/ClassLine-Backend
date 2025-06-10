package com.senai.classline.dto.Aluno;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.Turno;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

public record AlunoResponseDTO (

        String idInstituicao,

        String nome,

        String email,

        String cpf,
        Date dt_nascimento,

        String genero,
        String logradouro,
        String bairro,
        String numero,
        String cidade,

        String telefone,

        Turno turno,

        StatusPessoa status,

        Date dt_inicio,

        Date dt_fim,

        Long idTurma,

        Long idCurso
) {
}
