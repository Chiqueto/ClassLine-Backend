package com.senai.classline.dto.Nota;

import com.senai.classline.dto.Nota.NotaDetalhesDTO;

import java.util.Set;

public record NotaResponseDTO(
        int criadas,
        int atualizadas,
        String mensagem,
        Set<NotaDetalhesDTO> notas 
) {}
