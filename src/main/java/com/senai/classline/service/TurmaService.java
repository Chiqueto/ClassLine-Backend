package com.senai.classline.service;

import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.turma.TurmaDTO;
import com.senai.classline.dto.turma.TurmaEditarDTO;

import java.util.List;

public interface TurmaService {
    Turma criar (Long id_curso, TurmaDTO body);

    Turma editar(Long id_turma, TurmaEditarDTO body);

    Turma inativar(Long id_turma);

    Turma getTurmaById(Long id_turma);

    List<Turma> GetTurmaByCurso(Long id_curso);
}
