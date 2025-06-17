package com.senai.classline.dto.Aluno;

public record ComparativoMediaDisciplinaDTO(
        String nomeDisciplina,
        float mediaAluno,
        float mediaTurma
) {}