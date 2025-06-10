package com.senai.classline.dto.frequencia;


public record FrequenciaResponseDTO(
        Long idFrequencia,
        String idAluno,
        Long idAula,
        String idProfessor,
        Boolean presente
) {
}
