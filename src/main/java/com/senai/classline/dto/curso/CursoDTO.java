package com.senai.classline.dto.curso;

import com.senai.classline.enums.Tipo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CursoDTO(
        @NotNull(message = "Nome não pode ser nulo")
        @NotEmpty(message = "Nome não pode estar vazio")
        String nome,
        String descricao,
        @NotNull(message = "Qtde de semestres não pode ser nulo")
        int qtde_semestres,
        Tipo tipo) {
}
