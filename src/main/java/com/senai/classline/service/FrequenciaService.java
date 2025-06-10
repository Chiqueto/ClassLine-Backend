package com.senai.classline.service;

import com.senai.classline.dto.aula.AulaDTO;
import com.senai.classline.dto.frequencia.AlunoFrequenciaDTO;
import com.senai.classline.dto.frequencia.FrequenciaDTO;
import com.senai.classline.dto.frequencia.FrequenciaResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface FrequenciaService {
    List<FrequenciaResponseDTO> lancarFrequencia (List<FrequenciaDTO> body, Long idDisciplina, String idProfessor, AulaDTO aulaBody);

    List<AlunoFrequenciaDTO> getFrequenciaPorDiaDeAula(Long idDisciplina, String idProfessor, LocalDate data);
}
