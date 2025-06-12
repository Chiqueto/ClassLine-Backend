package com.senai.classline.service;

import com.senai.classline.domain.nota.Nota;
import com.senai.classline.dto.Nota.NotaDTO;

import java.util.Set;

public interface NotaService {
    Set<Nota> salvar(Set<NotaDTO> body);
}
