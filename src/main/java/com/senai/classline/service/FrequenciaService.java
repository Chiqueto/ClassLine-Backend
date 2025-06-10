package com.senai.classline.service;

import com.senai.classline.dto.frequencia.FrequenciaDTO;
import com.senai.classline.dto.frequencia.FrequenciaResponseDTO;

import java.util.List;

public interface FrequenciaService {
    FrequenciaResponseDTO lancarFrequencia (List<FrequenciaDTO> body, Long idDisciplina, String idProfessor);
}
