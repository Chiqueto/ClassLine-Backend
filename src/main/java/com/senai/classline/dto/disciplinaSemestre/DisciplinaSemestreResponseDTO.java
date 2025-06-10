package com.senai.classline.dto.disciplinaSemestre;


import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestreId;

// Um record simples para retornar os dados de forma limpa
public record DisciplinaSemestreResponseDTO(
        DisciplinaSemestreId id,
        String nomeDisciplina,
        int cargaHoraria,
        String nomeProfessor,
        int semestre
) {
    // Construtor de conveniência para mapear da entidade
    public DisciplinaSemestreResponseDTO(DisciplinaSemestre ds) {
        this(
                ds.getId(),
                ds.getDisciplina() != null ? ds.getDisciplina().getNome() : null,
                ds.getDisciplina() != null ? ds.getDisciplina().getCarga_horaria() : 0,
                ds.getProfessor() != null ? ds.getProfessor().getNome() : null,
                ds.getSemestre() != null ? ds.getSemestre().getSemestre() : null
        );
    }
}