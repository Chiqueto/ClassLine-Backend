package com.senai.classline.domain.avaliacao;

import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.nota.Nota;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.enums.TipoProva;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "avaliacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Long idAvaliacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_professor", referencedColumnName = "id_professor")
    private Professor professor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_turma", referencedColumnName = "id_turma")
    private Turma turma;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_disciplina", referencedColumnName = "id_disciplina")
    private Disciplina disciplina;
    @NotNull
    @Enumerated(value= EnumType.STRING)
    private TipoProva tipo;
    @NotNull
    private String nome;
    @NotNull
    private float peso;
    @NotNull
    private LocalDate data;
    @NotNull
    private Boolean concluida;
    @OneToMany(mappedBy = "idNota", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Nota> nota;
}
