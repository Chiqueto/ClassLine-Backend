package com.senai.classline.dto.turma;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.grade.Grade;
import com.senai.classline.dto.curso.CursoResponseDTO;
import com.senai.classline.enums.Turno;
import jakarta.persistence.*;

import java.util.Date;

public record TurmaResponseDTO (
     Long idTurma,
     String nome,
     String observacao,
     Turno turno,
     Date dt_inicio,
     Date dt_fim,
     Boolean ativo,
     CursoResponseDTO curso,
     Long idGrade
)
{
}
