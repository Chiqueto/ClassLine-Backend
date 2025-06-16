package com.senai.classline.service;

import com.senai.classline.dto.avaliacao.AvaliacaoDTO;
import com.senai.classline.dto.avaliacao.AvaliacaoResponseDTO;

import java.util.List;
import java.util.Set;

public interface AvaliacaoService {
    AvaliacaoResponseDTO criar (AvaliacaoDTO body, Long idDisciplina, String idProfessor, Long idTurma);

    List<AvaliacaoResponseDTO> getByProfessorTurmaAndDisciplina (String idProfessor, Long idDisciplina, Long idTurma);

    Set<AvaliacaoResponseDTO> getByAluno (String idAluno);
}
