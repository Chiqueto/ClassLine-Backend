package com.senai.classline.domain.turma;

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
    public String idTurma;
    public String nome;
    public String observacao;
    public Turno turno;
    public Date dt_inicio;
    public Date dt_fim;
    public Boolean ativo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    public Curso curso;
    public Long id_grade;


}
