package com.senai.classline.dto.aula;

import java.time.LocalDate;
import java.util.Date;

public record AulaResponseDTO(
        Long idAula,
        String idProfessor,
        Long idDisciplina,
        LocalDate data,
        String conteudo
) {
}
