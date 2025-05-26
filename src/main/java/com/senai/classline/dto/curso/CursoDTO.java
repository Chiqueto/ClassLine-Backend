package com.senai.classline.dto.curso;

import com.senai.classline.enums.Tipo;

public record CursoDTO(Long id_curso,
                       String id_instituicao,
                       String nome,
                       String descricao,
                       int qtde_semestres,
                       Tipo tipo) {
}
