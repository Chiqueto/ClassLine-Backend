package com.senai.classline.dto;

import com.senai.classline.enums.Tipo;

public record CursoDTO(Long id_curso,
                       Long id_instituicao,
                       String nome,
                       String descricao,
                       int qtde_semestre,
                       Tipo tipo) {
}
