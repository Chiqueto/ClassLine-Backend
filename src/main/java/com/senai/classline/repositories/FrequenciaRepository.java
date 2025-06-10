package com.senai.classline.repositories;

import com.senai.classline.domain.frequencia.Frequencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {
}
