package com.senai.classline.domain.semestre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.grade.Grade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "semestre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_semestre")
    private Long idSemestre;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grade", referencedColumnName = "id_grade")
    private Grade grade;
    private int semestre;
    @OneToMany(mappedBy = "semestre", fetch = FetchType.LAZY)
    private List<DisciplinaSemestre> disciplinasSemestre;
    private Date dt_inicio;
    private Date dt_fim;
}
