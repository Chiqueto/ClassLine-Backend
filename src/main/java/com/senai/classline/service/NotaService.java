package com.senai.classline.service;

import com.senai.classline.domain.nota.Nota;
import com.senai.classline.dto.Nota.NotaDTO;
import com.senai.classline.dto.Nota.NotaDetalhesDTO;
import com.senai.classline.dto.Nota.NotaResponseDTO;

import java.util.Set;

public interface NotaService {
    NotaResponseDTO salvar(Set<NotaDTO> body, Long idAvaliacao);

    Set<NotaDetalhesDTO> getNotasByAvaliacao(Long idAvaliacao);
}
