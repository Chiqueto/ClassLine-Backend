package com.senai.classline.domain.aula;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.enums.Tipo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "aula")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    private Long idAula;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_professor", referencedColumnName = "id_professor")
    private Professor professor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_disciplina", referencedColumnName = "id_disciplina")
    private Disciplina disciplina;
    private Date data;
    private String conteudo;

}
