package com.senai.classline.service;

import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.PessoaLoginRequestDTO;
import com.senai.classline.dto.professor.ProfessorDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.dto.professor.ProfessorEditarDTO;

public interface ProfessorService {
    Professor salvar (ProfessorDTO professorDTO, String id_instituicao);

    Professor editar (String idInstituicao, ProfessorEditarDTO body, String id_instituicao);

    Professor inativar (String id, String id_instituicao);


    Professor converteDTO (ProfessorDTO body);

    ResponseDTO login (PessoaLoginRequestDTO body);
}
