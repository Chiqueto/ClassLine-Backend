package com.senai.classline.service;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.dto.Aluno.*;
import com.senai.classline.dto.PessoaLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;

import java.util.List;

public interface AlunoService {
    Aluno salvar (AlunoDTO alunoDTO);

    AlunoResponseDTO editar (AlunoEditarDTO body, String id_aluno);

    AlunoResponseDTO inativar (String id_aluno);

    ResponseDTO login (PessoaLoginRequestDTO body);

    AlunoResponseDTO getById(String idAluno);

    List<AlunoResponseDTO> getByTurma(Long idTurma);
    List<AlunoResponseDTO> getByCurso(Long idCurso);
    List<AlunoResponseDTO> getByInstituicao(String idInstituicao);
    List<AlunoDisciplinaDTO> getAlunoByDisciplina(Long idDisciplina);
    AlunoBoletimDTO getBoletimByAluno(String idAluno);

}
