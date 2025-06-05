package com.senai.classline.repositories;

import com.senai.classline.domain.disciplina.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    Optional<Disciplina> findByNome(String nome);
    List<Disciplina> findByInstituicao_IdInstituicao(String idInstituicao);

}
