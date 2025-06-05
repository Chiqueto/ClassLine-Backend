package com.senai.classline.dto.disciplina;



public record DisciplinaResponseDTO (
        Long idDisciplina,
        String nome,
        int carga_horaria,
        boolean status,
        String idInstituicao
) {}
