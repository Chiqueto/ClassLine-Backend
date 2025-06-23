package com.senai.classline.dto.Aluno;

public record AlunoDesempenhoDTO(
        String idAluno,
        String nomeAluno,
        float mediaFinal,
        float percentualFrequencia
) {}
