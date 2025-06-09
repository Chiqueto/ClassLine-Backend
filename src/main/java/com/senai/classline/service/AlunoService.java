package com.senai.classline.service;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.Aluno.AlunoDTO;
import com.senai.classline.dto.Aluno.AlunoEditarDTO;
import com.senai.classline.dto.PessoaLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.dto.professor.ProfessorDTO;
import com.senai.classline.dto.professor.ProfessorEditarDTO;

import java.util.List;

public interface AlunoService {
    Aluno salvar (AlunoDTO alunoDTO);

    Aluno editar (AlunoEditarDTO body, String id_aluno);

    Aluno inativar (String id_aluno);

    ResponseDTO login (PessoaLoginRequestDTO body);

    Aluno getById(String idAluno);

    List<Aluno> getByTurma(Long idTurma);
    List<Aluno> getByCurso(Long idCurso);
    List<Aluno> getByInstituicao(String idInstituicao);
    List<Aluno> getAlunoByDisciplina(Long idDisciplina);

}
