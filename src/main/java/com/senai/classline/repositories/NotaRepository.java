package com.senai.classline.repositories;

import com.senai.classline.domain.nota.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
