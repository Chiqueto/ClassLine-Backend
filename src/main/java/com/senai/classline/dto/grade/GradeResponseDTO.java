package com.senai.classline.dto.grade;

import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.domain.turma.Turma;
import jakarta.persistence.*;

import java.util.List;

public record GradeResponseDTO (
        Long idGrade,
        String descricao
) {
}
