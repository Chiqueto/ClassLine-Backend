package com.senai.classline.dto.curso;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.enums.Tipo;
import jakarta.persistence.*;

import java.util.List;

public record CursoResponseDTO(
Long idCurso,
Instituicao instituicao,
String nome,
String descricao,
int qtde_semestres,
Tipo tipo,
Boolean ativo
) {
}
