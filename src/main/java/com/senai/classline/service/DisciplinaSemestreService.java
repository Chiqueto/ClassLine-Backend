package com.senai.classline.service;

import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;

public interface DisciplinaSemestreService {
    DisciplinaSemestre addDisciplina (Long id_disciplina, Long id_semestre, String id_professor);

    DisciplinaSemestre concludeSemester (Long id_disciplina, Long id_semestre, String id_professor);

    DisciplinaSemestre inactivateSemester (Long id_disciplina, Long id_semestre, String id_professor);

//    DisciplinaSemestre editSemester (Long id_disciplina, Long id_semestre, String id_professor);
}
