package com.senai.classline.repositories;

import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.instituicao.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    Optional<Disciplina> findByNome(String nome);
    List<Disciplina> findByInstituicao_IdInstituicao(String idInstituicao);


    List<Disciplina> findAllByInstituicao(Instituicao instituicao);

    Optional<Disciplina> findByIdDisciplinaAndInstituicao(Long idDisciplina, Instituicao instituicao);

    @Query("SELECT DISTINCT d FROM Disciplina d " +
            "JOIN d.disciplinasSemestre ds " +
            "JOIN ds.semestre s " +
            "JOIN s.grade g " +
            "WHERE g.turma.id = :turmaId")
    List<Disciplina> findDisciplinasByTurmaId(@Param("turmaId") Long turmaId);

    List<Disciplina> findDistinctByDisciplinasSemestreProfessorIdProfessor(String idProfessor);

}
