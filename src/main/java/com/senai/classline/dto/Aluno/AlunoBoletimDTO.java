package com.senai.classline.dto.Aluno;

import com.senai.classline.dto.avaliacao.AvaliacaoBoletimDTO;
import com.senai.classline.dto.disciplina.DisciplinaBoletimDTO;

import java.util.Set;

public record AlunoBoletimDTO (
        Set<DisciplinaBoletimDTO> boletim
)
{ }
