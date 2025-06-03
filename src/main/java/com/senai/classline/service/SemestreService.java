package com.senai.classline.service;

import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.dto.semestre.SemestreResponseDTO;

import java.util.List;

public interface SemestreService {
    List<SemestreResponseDTO> getSemestreByGrade(Long idGrade);
}
