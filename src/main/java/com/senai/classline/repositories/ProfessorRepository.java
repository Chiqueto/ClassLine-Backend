package com.senai.classline.repositories;

import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
    Optional<Professor> findByCpf(String cpf);

    Optional<Professor> findByIdProfessor(String IdProfessor);

    Optional<Professor> findByEmail(String email);

    List<Professor> findByInstituicao_idInstituicao(String idInstituicao);

}
