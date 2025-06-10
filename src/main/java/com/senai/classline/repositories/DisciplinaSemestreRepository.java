package com.senai.classline.repositories;

import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaSemestreRepository extends JpaRepository<DisciplinaSemestre, DisciplinaSemestreId> {
    @Query("SELECT ds FROM DisciplinaSemestre ds " +
            "JOIN ds.semestre.grade g " +
            "JOIN g.turma t " +
            "WHERE t.idTurma = :idTurma")
    List<DisciplinaSemestre> findByTurmaId(@Param("idTurma") Long idTurma);
}
