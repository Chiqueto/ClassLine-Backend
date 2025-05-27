package com.senai.classline.dto.turma;

import com.senai.classline.enums.Turno;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record TurmaEditarDTO(
        String nome,
        String observacao,
        Turno turno,
        Date dt_inicio,
        Date dt_fim
) {

}
