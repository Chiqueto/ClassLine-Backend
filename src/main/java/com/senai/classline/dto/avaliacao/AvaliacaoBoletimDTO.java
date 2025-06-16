package com.senai.classline.dto.avaliacao;

import com.senai.classline.dto.Nota.NotaBoletimDTO;
import com.senai.classline.enums.TipoProva;

import java.time.LocalDate;

public record AvaliacaoBoletimDTO(
        Long idAvaliacao,
        String nomeDisciplina,
        TipoProva tipo,
        Float peso,
        LocalDate data,
        Boolean concluida,
        NotaBoletimDTO nota
) {
}
