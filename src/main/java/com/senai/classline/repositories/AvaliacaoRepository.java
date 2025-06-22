package com.senai.classline.repositories;

import com.senai.classline.domain.avaliacao.Avaliacao;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.turma.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByTurmaIdTurmaAndProfessorIdProfessorAndDisciplinaIdDisciplina(
            Long idTurma,
            String idProfessor,
            Long idDisciplina
    );

    List<Avaliacao> findByTurmaAndProfessorAndDisciplina(
            Turma turma,
            Professor professor,
            Disciplina disciplina

    );

    @Query("SELECT a FROM Avaliacao a " +
            "JOIN a.turma t " +
            "JOIN t.aluno al " +
            "WHERE al.idAluno like :idAluno")
    Set<Avaliacao> findByAluno(@Param("idAluno") String idAluno);
}
