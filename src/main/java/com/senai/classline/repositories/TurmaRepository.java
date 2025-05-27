package com.senai.classline.repositories;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.turma.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {
}