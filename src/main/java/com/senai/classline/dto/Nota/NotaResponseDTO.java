package com.senai.classline.dto.Nota;

import com.senai.classline.domain.nota.Nota;

import java.util.Set;

public record NotaResponseDTO(
        int criadas,
        int atualizadas,
        String mensagem,
        Set<NotaDetalhesDTO> notas // <-- CORRIGIDO! Agora usa o DTO de detalhes.
) {}
