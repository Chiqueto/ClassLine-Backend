package com.senai.classline.dto.disciplina;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DisciplinaDTO (
    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode estar vazio")
    String nome,
    @NotNull(message = "Carga horária não pode ser nula")
    int carga_horaria
) {}
