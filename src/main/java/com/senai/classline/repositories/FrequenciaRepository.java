package com.senai.classline.repositories;


import com.senai.classline.domain.frequencia.Frequencia;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {

    @Query("SELECT f FROM Frequencia f JOIN FETCH f.aluno WHERE f.aula.id = :idAula")
    List<Frequencia> findByAulaIdComAlunos(@Param("idAula") Long idAula);

    List<Frequencia> findByAluno_IdAluno(@Param("idAluno") String idAluno);

    @EntityGraph(attributePaths = {"aluno"})
    List<Frequencia> findByAula_IdAula(Long idAula);
}
