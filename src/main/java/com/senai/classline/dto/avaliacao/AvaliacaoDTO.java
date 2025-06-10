package com.senai.classline.dto.avaliacao;

import com.senai.classline.enums.TipoProva;

import java.time.LocalDate;

public record AvaliacaoDTO(
        TipoProva tipo,
        String nome,
        Float peso,
        LocalDate data
) {
}
