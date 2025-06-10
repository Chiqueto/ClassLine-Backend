package com.senai.classline.domain.frequencia;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.aula.Aula;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.professor.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "frequencia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Frequencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_frequencia")
    private Long idFrequencia;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", referencedColumnName = "id_aluno")
    private Aluno aluno;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_professor", referencedColumnName = "id_professor")
    private Professor professor;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aula", referencedColumnName = "id_aula")
    private Aula aula;
    private Boolean presente;
}
