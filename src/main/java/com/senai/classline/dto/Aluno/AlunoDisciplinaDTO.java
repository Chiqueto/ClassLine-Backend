package com.senai.classline.dto.Aluno;

import com.senai.classline.dto.avaliacao.AvaliacaoBoletimDTO;

import java.util.Set;

public record AlunoDisciplinaDTO(
        String idAluno,
        String nomeAluno,
        String emailAluno,
        Long idDisciplina,
        String nomeDisciplina,
        float media,
        float frequencia
) {
}
