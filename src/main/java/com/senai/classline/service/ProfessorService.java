package com.senai.classline.service;

import com.senai.classline.domain.professor.Professor;

public interface ProfessorService {
    Professor salvar (Professor professor);

    Professor editar (Professor professor);

    void validar (Professor professor);
}
