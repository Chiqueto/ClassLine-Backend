package com.senai.classline.repositories;

import com.senai.classline.domain.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
    Optional<Professor> findByCpf(String cpf);

    Optional<Professor> findByIdProfessor(String IdProfessor);

    Optional<Professor> findByEmail(String email);
}
