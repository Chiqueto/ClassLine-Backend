package com.senai.classline.dto.aula;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record AulaDTO(
        @NotNull
        Date data,
        @NotNull
        @NotEmpty
        String conteudo
) {
}
