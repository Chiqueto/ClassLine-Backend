package com.senai.classline.domain.turma;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.senai.classline.domain.curso.Curso;
import com.senai.classline.enums.Turno;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "turma")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    private Long idTurma;
    private String nome;
    private String observacao;
    @Enumerated(value= EnumType.STRING)
    private Turno turno;
    private Date dt_inicio;
    private Date dt_fim;
    private Boolean ativo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    private Curso curso;
    private Long id_grade;
}
