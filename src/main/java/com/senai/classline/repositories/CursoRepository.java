package com.senai.classline.repositories;

import com.senai.classline.domain.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
    Optional<Curso> findByNome(String nome);
    List<Curso> findByInstituicao_IdInstituicao(String idInstituicao);
}
