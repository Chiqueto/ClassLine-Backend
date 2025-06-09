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

@Entity
@Table(name = "disciplinasemestre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinaSemestre {

    @EmbeddedId
    private DisciplinaSemestreId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idDisciplina") // Mapeia o campo 'idDisciplina' da classe DisciplinaSemestreId
    @JoinColumn(name = "id_disciplina")
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSemestre") // Mapeia o campo 'idSemestre' da classe DisciplinaSemestreId
    @JoinColumn(name = "id_semestre")
    private Semestre semestre;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProfessor") // Mapeia o campo 'idProfessor' da classe DisciplinaSemestreId
    @JoinColumn(name = "id_professor")
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusSemestre status;
}
