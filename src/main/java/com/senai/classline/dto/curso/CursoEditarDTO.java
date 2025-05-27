package com.senai.classline.dto.curso;

import com.senai.classline.enums.Tipo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CursoEditarDTO (
    String nome,
    String descricao,
    int qtde_semestres,
    Tipo tipo
)
{}
