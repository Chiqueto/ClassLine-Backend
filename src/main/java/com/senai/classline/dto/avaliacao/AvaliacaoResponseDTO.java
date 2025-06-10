package com.senai.classline.dto.avaliacao;

import com.senai.classline.domain.avaliacao.Avaliacao;
import com.senai.classline.enums.TipoProva;

import java.time.LocalDate;

public record AvaliacaoResponseDTO(
        Long idAvaliacao,
        Long idDisciplina,
        String nomeDisciplina,
        TipoProva tipo,
        String nome,
        Float peso,
        LocalDate data,
        Boolean concluida
){

    public AvaliacaoResponseDTO(Avaliacao avaliacao) {
        this(
                avaliacao.getIdAvaliacao(),
                avaliacao.getDisciplina() != null ? avaliacao.getDisciplina().getIdDisciplina() : null,
                avaliacao.getDisciplina() != null ? avaliacao.getDisciplina().getNome() : null,
                avaliacao.getTipo(),
                avaliacao.getNome(),
                avaliacao.getPeso(),
                avaliacao.getData(),
                avaliacao.getConcluida()
        );
    }
}
