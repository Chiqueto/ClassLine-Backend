package com.senai.classline.dto.curso;

import com.senai.classline.enums.Tipo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CursoDTO(
        @NotNull(message = "Id da Instituição não pode ser nulo")
        @NotEmpty(message = "Id da Instituição não pode estar vazio")
        String idInstituicao,
        @NotNull(message = "Nome não pode ser nulo")
        @NotEmpty(message = "Nome não pode estar vazio")
        String nome,
        String descricao,
        @NotNull(message = "Qtde de semestres não pode ser nulo")
        @NotEmpty(message = "Qtde de semestres não pode estar vazio")
        int qtde_semestres,
        @NotNull(message = "Tipo não pode ser nulo")
        @NotEmpty(message = "Tipo não pode estar vazio")
        Tipo tipo) {
}
