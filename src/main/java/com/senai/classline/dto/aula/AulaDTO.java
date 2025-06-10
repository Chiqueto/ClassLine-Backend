package com.senai.classline.dto.aula;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public record AulaDTO(
        @NotNull
        LocalDate data,
        @NotNull
        @NotEmpty
        String conteudo
) {
}
