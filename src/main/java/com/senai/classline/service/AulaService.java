package com.senai.classline.service;

import com.senai.classline.dto.aula.AulaDTO;
import com.senai.classline.dto.aula.AulaResponseDTO;

public interface AulaService {
    AulaResponseDTO criar (String idProfessor, Long idDisciplina, AulaDTO body);
}
