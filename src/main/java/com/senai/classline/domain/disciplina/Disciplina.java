package com.senai.classline.domain.disciplina;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.enums.Tipo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "disciplina")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplina")
    private Long idDisciplina;
    private String nome;
    private int carga_horaria;
    private boolean status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instituicao", referencedColumnName = "id_instituicao")
    private Instituicao instituicao;
    @OneToMany(mappedBy = "disciplina", fetch = FetchType.LAZY)
    private List<DisciplinaSemestre> disciplinasSemestre;


}
