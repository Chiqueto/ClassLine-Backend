package com.senai.classline.dto.disciplinaSemestre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TrocarProfessorDTO(

        @NotNull(message = "O ID da disciplina não pode ser nulo.")
        Long idDisciplina,

        @NotNull(message = "O ID do semestre não pode ser nulo.")
        Long idSemestre,

        @NotBlank(message = "O ID do professor antigo não pode ser nulo ou vazio.")
        String idProfessorAntigo,

        @NotBlank(message = "O ID do novo professor não pode ser nulo ou vazio.")
        String idProfessorNovo
) {
}