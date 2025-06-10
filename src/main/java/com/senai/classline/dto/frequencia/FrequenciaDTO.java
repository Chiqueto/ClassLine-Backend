package com.senai.classline.dto.frequencia;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FrequenciaDTO(
        @NotNull
        @NotEmpty
        String idAluno,
        @NotNull
        Boolean presente
) {
}
