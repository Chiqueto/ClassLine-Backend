package com.senai.classline.dto.semestre;

import com.senai.classline.domain.grade.Grade;
import com.senai.classline.dto.grade.GradeResponseDTO;

public record SemestreResponseDTO(
        Long idSemestre,
        GradeResponseDTO grade,
        int semestre
)
{}
