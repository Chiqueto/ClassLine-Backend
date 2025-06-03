package com.senai.classline.repositories;

import com.senai.classline.domain.semestre.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SemestreRepository extends JpaRepository<Semestre, Long> {
    List<Semestre> findByGrade_idGrade(Long idGrade);
}
