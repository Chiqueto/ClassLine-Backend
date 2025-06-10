package com.senai.classline.dto.aula;

import java.util.Date;

public record AulaResponseDTO(
        Long idAula,
        String idProfessor,
        Long idDisciplina,
        Date data,
        String conteudo
) {
}
