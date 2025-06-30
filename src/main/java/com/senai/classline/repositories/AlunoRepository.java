package com.senai.classline.repositories;

import com.senai.classline.domain.aluno.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, String> {
    Optional<Aluno> findByCpf(String cpf);

    Optional<Aluno> findByEmail(String email);

    List<Aluno> findByCurso_idCurso(Long idCurso);
    List<Aluno> findByTurma_idTurma(Long idTurma);

    @Query("SELECT DISTINCT a FROM Aluno a " +
            "JOIN a.turma t " +
            "JOIN t.grade g " +          
            "JOIN g.semestre s " +        
            "JOIN s.disciplinasSemestre ds " +
            "WHERE ds.disciplina.id = :disciplinaId")
    List<Aluno> findAlunosByDisciplinaId(@Param("disciplinaId") Long disciplinaId);
}

