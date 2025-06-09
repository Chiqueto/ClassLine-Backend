package com.senai.classline.domain.disciplinaSemestre;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinaSemestreId implements Serializable {

    @Column(name = "id_disciplina")
    private Long idDisciplina;

    @Column(name = "id_semestre")
    private Long idSemestre;

    // Alterado para String para corresponder à sua interface
    @Column(name = "id_professor")
    private String idProfessor;

    // Construtores, Getters e Setters gerados pelo Lombok

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisciplinaSemestreId that = (DisciplinaSemestreId) o;
        return Objects.equals(idDisciplina, that.idDisciplina) &&
                Objects.equals(idSemestre, that.idSemestre) &&
                Objects.equals(idProfessor, that.idProfessor); // Comparação com String
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDisciplina, idSemestre, idProfessor);
    }
}
