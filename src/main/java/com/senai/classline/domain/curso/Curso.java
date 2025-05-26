package com.senai.classline.domain.curso;

import com.senai.classline.enums.Tipo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "curso")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;
    @Column(name = "id_instituicao")
    private String idInstituicao;
    private String nome;
    private String descricao;
    private int qtde_semestres;
    @Enumerated(value= EnumType.STRING)
    private Tipo tipo;
}
