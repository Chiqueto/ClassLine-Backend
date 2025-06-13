package com.senai.classline.dto.Nota;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotaDTO(
        @NotNull(message = "Nota não pode ser nula")
        float valor,
        @NotNull(message = "Id do aluno não pode ser nulo")
        @NotBlank(message= "Id do aluno não pode estar vazio")
        String idAluno
) {
}
