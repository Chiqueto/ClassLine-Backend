package com.senai.classline.dto.frequencia;

import com.senai.classline.dto.aula.AulaDTO;

import java.util.List;

public record LancarFrequenciaRequest(
        AulaDTO aula,
        List<FrequenciaDTO> presencas
) {
}
