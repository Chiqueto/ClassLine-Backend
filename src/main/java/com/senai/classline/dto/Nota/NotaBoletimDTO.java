package com.senai.classline.dto.Nota;

import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.nota.Nota;

public record NotaBoletimDTO(
        Long idNota,
        float valor
) {
    public NotaBoletimDTO(Nota nota) {
        this(
                nota.getIdNota(),
                nota.getValor() != null ? nota.getValor() : 0
        );
    }
}
