package com.senai.classline.dto.disciplina;

import com.senai.classline.dto.avaliacao.AvaliacaoBoletimDTO;

import java.util.Set;

public record DisciplinaBoletimDTO(
        Long idDisciplina,
        String nomeDisciplina,

        Set<AvaliacaoBoletimDTO> avaliacoes,
        float frequencia
) {
}
