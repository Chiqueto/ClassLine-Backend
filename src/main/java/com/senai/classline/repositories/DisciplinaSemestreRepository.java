package com.senai.classline.repositories;

import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaSemestreRepository extends JpaRepository<DisciplinaSemestre, DisciplinaSemestreId> {

}
