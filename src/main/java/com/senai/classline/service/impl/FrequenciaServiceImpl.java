package com.senai.classline.service.impl;

import com.senai.classline.dto.frequencia.FrequenciaDTO;
import com.senai.classline.dto.frequencia.FrequenciaResponseDTO;
import com.senai.classline.service.FrequenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FrequenciaServiceImpl implements FrequenciaService {
    @Override
    public FrequenciaResponseDTO lancarFrequencia(List<FrequenciaDTO> body, Long idDisciplina, String idProfessor) {
        return null;
    }
}
