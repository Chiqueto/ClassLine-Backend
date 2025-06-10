package com.senai.classline.domain.aluno;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.domain.pessoa.Pessoa;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.enums.Formacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "aluno")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Aluno extends Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_aluno")
    private String idAluno;
    @JoinColumn(name = "id_turma", referencedColumnName = "id_turma")
    @ManyToOne(fetch = FetchType.LAZY)

    private Turma turma;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne(fetch = FetchType.LAZY)
    
    private Curso curso;
    private Date dt_inicio;
    private Date dt_fim;


    @Override
    public String getLoginIdentifier() {
        return this.getCpf();
    }

}
