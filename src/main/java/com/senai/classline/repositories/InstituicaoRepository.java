package com.senai.classline.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.senai.classline.domain.instituicao.Instituicao;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer> {
	Optional<Instituicao> findByEmail(String email);
	Optional<Instituicao> findByIdInstituicao(String idInstituicao);
}
