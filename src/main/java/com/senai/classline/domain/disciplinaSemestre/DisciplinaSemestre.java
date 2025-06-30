package com.senai.classline.domain.disciplinaSemestre;

import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.enums.StatusSemestre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "disciplinasemestre")
@Getter
@Setter
@SQLRestriction("status <> 'INATIVO'")
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinaSemestre {

    @EmbeddedId
    private DisciplinaSemestreId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idDisciplina") 
    @JoinColumn(name = "id_disciplina")
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSemestre") 
    @JoinColumn(name = "id_semestre")
    private Semestre semestre;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProfessor")
    @JoinColumn(name = "id_professor")
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusSemestre status;
}
