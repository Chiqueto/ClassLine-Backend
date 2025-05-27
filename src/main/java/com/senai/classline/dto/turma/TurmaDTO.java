package com.senai.classline.dto.turma;


import com.senai.classline.enums.Turno;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record TurmaDTO(
        @NotNull(message = "Nome não pode ser nulo")
        @NotEmpty(message = "Nome não pode estar vazio")
        String nome,
        String observacao,
        Turno turno,
        @NotNull(message = "Data de início não pode ser nulo")
        Date dt_inicio,
        @NotNull(message = "Data de finalização não pode ser nulo")
        Date dt_fim
) {}
