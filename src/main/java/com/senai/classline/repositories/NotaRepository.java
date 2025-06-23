package com.senai.classline.repositories;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.avaliacao.Avaliacao;
import com.senai.classline.domain.nota.Nota;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    Optional<Nota> findByAlunoAndAvaliacao (Aluno aluno, Avaliacao avaliacao);

    List<Nota> findAllByAlunoInAndAvaliacao(List<Aluno> alunos, Avaliacao avaliacao);

    @EntityGraph(attributePaths = {"aluno"})
    List<Nota> findAllByAvaliacaoIdAvaliacao(Long idAvaliacao);

    List<Nota> findByAluno_IdAluno(String idAvaliacao);

    @EntityGraph(attributePaths = {"aluno", "avaliacao"})
    List<Nota> findByAvaliacao_Disciplina_IdDisciplinaAndAlunoIn(Long disciplinaId, List<Aluno> alunos);

    @EntityGraph(attributePaths = {"aluno", "avaliacao.disciplina"})
    List<Nota> findByAvaliacao_Turma_IdTurma(Long turmaId);

    List<Nota> findByAvaliacao_Turma_IdTurmaAndAvaliacao_Disciplina_IdDisciplina(
            Long idTurma, Long idDisciplina
    );
}
