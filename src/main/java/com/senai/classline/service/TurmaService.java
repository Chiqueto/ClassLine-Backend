package com.senai.classline.service;

import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.turma.TurmaDTO;
import com.senai.classline.dto.turma.TurmaEditarDTO;

public interface TurmaService {
    Turma criar (Long id_curso, TurmaDTO body);

    Turma editar(Long id_turma, TurmaEditarDTO body);

    Turma inativa(Long id_turma);
}
