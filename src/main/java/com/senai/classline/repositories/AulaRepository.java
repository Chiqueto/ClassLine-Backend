package com.senai.classline.repositories;

import com.senai.classline.domain.aula.Aula;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    boolean existsByProfessorAndDisciplinaAndData(Professor professor, Disciplina disciplina, LocalDate data);

    Optional<Aula> findByProfessorAndDisciplinaAndData(Professor professor, Disciplina disciplina, LocalDate data);
}

