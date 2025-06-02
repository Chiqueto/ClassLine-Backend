package com.senai.classline.repositories;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.turma.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByCurso_idCurso(Long idCurso);

}
