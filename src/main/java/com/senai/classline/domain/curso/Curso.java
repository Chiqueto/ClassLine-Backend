package com.senai.classline.domain.curso;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.enums.Tipo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "curso")
@Getter
@Setter
@SQLRestriction("ativo = true")
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_instituicao", referencedColumnName = "id_instituicao")
    private Instituicao instituicao;
    private String nome;
    private String descricao;
    private int qtde_semestres;
    @Enumerated(value= EnumType.STRING)
    private Tipo tipo;
    private Boolean ativo;
    @OneToMany(mappedBy = "curso")
    @JsonBackReference
    private List<Turma> turmas;
}
