package com.senai.classline.repositories;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.turma.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByCurso_idCurso(Long idCurso);

    @Query("SELECT DISTINCT t FROM Turma t " +
            "JOIN t.grade gc " +
            "JOIN gc.semestre s " +
            "JOIN s.disciplinasSemestre ds " +
            "WHERE ds.professor.id = :professorId")
    List<Turma> findTurmasByProfessorId(@Param("professorId") String professorId);

}
