package com.senai.classline.service;

import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.ProfessorDTO;

public interface ProfessorService {
    Professor salvar (ProfessorDTO professorDTO, String id_instituicao);

    Professor editar (Professor professor);

    Professor inativar (String id, String id_instituicao);

    void validar (Professor professor);

    Professor converteDTO (ProfessorDTO body);

}
