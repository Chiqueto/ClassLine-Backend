package com.senai.classline.service;

import com.senai.classline.dto.disciplina.DisciplinaDTO;
import com.senai.classline.dto.disciplina.DisciplinaResponseDTO;

import java.util.List;

public interface DisciplinaService {
    DisciplinaResponseDTO criar(String id_instituicao, DisciplinaDTO body);

    DisciplinaResponseDTO inativar(Long id_disciplina, String id_instituicao);

    DisciplinaResponseDTO getDisciplinaById(Long id_disciplina);

    List<DisciplinaResponseDTO> getDisciplinaByInstituicao(String id_instituicao);

    List<DisciplinaResponseDTO> getDisciplinaByTurma(Long id_turma);
}
