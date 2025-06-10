package com.senai.classline.service;

import com.senai.classline.dto.avaliacao.AvaliacaoDTO;
import com.senai.classline.dto.avaliacao.AvaliacaoResponseDTO;

public interface AvaliacaoService {
    AvaliacaoResponseDTO criar (AvaliacaoDTO body, Long idDisciplina, String idProfessor, Long idTurma);
}
