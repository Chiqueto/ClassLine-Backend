package com.senai.classline.service;

import com.senai.classline.dto.avaliacao.AvaliacaoDTO;
import com.senai.classline.dto.avaliacao.AvaliacaoResponseDTO;

import java.util.List;

public interface AvaliacaoService {
    AvaliacaoResponseDTO criar (AvaliacaoDTO body, Long idDisciplina, String idProfessor, Long idTurma);

    List<AvaliacaoResponseDTO> getByProfessorTurmaAndDisciplina (String idProfessor, Long idDisciplina, Long idTurma);
}
