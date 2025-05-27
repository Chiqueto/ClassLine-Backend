package com.senai.classline.service;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.dto.curso.CursoDTO;
import com.senai.classline.dto.curso.CursoEditarDTO;

public interface CursoService {
    Curso criar(String id_instituicao, CursoDTO body);

    Curso editar(Long id_curso, CursoEditarDTO body, String id_instituicao);

    Curso inativar(Long id_curso, String id_instituicao);
}
