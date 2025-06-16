package com.senai.classline.repositories;

import com.senai.classline.domain.avaliacao.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    @Query("SELECT a FROM Avaliacao a " +
            "WHERE a.disciplina.idDisciplina = :idDisciplina " +
            "AND EXISTS (" +
            "  SELECT 1 FROM DisciplinaSemestre ds " +
            "  JOIN ds.semestre.grade g " +
            "  JOIN g.turma t " +
            "  WHERE ds.disciplina.idDisciplina = :idDisciplina " +
            "  AND ds.professor.idProfessor = :idProfessor " +
            "  AND t.idTurma = :idTurma" +
            ")")
    List<Avaliacao> findByTurmaAndProfessorAndDisciplina(
            @Param("idTurma") Long idTurma,
            @Param("idProfessor") String idProfessor,
            @Param("idDisciplina") Long idDisciplina
    );

    @Query("SELECT a FROM Avaliacao a " +
            "JOIN a.turma t " +
            "JOIN t.aluno al " +
            "WHERE al.idAluno like :idAluno")
    Set<Avaliacao> findByAluno(@Param("idAluno") String idAluno);
}
