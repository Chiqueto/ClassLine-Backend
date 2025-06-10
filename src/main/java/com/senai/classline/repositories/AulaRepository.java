package com.senai.classline.repositories;

import com.senai.classline.domain.aula.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaRepository extends JpaRepository<Aula, Long> {
}
