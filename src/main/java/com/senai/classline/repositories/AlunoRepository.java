package com.senai.classline.repositories;

import com.senai.classline.domain.aluno.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, String> {
    Optional<Aluno> findByCpf(String cpf);

    Optional<Aluno> findByEmail(String email);

    List<Aluno> findByCurso_idCurso(Long idCurso);
    List<Aluno> findByTurma_idTurma(Long idTurma);
}

