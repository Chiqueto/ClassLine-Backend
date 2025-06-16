package com.senai.classline.service;

import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.dto.disciplinaSemestre.DisciplinaSemestreResponseDTO;

import java.util.List;
import java.util.Set;

public interface DisciplinaSemestreService {
    DisciplinaSemestre addDisciplina (Long id_disciplina, Long id_semestre, String id_professor);

    DisciplinaSemestre concludeSemester (Long id_disciplina, Long id_semestre, String id_professor);

    DisciplinaSemestre inactivateSemester (Long id_disciplina, Long id_semestre, String id_professor);

    List<DisciplinaSemestreResponseDTO> getGradeByTurma (Long idTurma);
//    DisciplinaSemestre editSemester (Long id_disciplina, Long id_semestre, String id_professor);
    Set<DisciplinaSemestreResponseDTO> getDisciplinasSemestreByAluno(String id_aluno);
}
