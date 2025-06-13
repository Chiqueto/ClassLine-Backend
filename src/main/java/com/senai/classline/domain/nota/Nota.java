package com.senai.classline.domain.nota;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.avaliacao.Avaliacao;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nota")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Long idNota;
    private Float valor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_avaliacao", referencedColumnName = "id_avaliacao")
    private Avaliacao avaliacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", referencedColumnName = "id_aluno")
    private Aluno aluno;
}
