package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.avaliacao.Avaliacao;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.avaliacao.AvaliacaoDTO;
import com.senai.classline.dto.avaliacao.AvaliacaoResponseDTO;
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.repositories.*;
import com.senai.classline.service.AvaliacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoServiceImpl implements AvaliacaoService {
    final AvaliacaoRepository repository;
    final DisciplinaRepository disciplinaRepository;
    final ProfessorRepository professorRepository;
    final TurmaRepository turmaRepository;
    final AlunoRepository alunoRepository;

    @Override
    public AvaliacaoResponseDTO criar(AvaliacaoDTO body, Long idDisciplina, String idProfessor, Long idTurma) {
        Disciplina disciplina = this.disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada com ID: " + idDisciplina));
        Professor professor = this.professorRepository.findById(idProfessor)
                .orElseThrow(() -> new NotFoundException("Professor não encontrada com ID: " + idProfessor));
        Turma turma = this.turmaRepository.findById(idTurma)
                .orElseThrow(() -> new NotFoundException("Turma não encontrada com ID: " + idTurma));

        Avaliacao newAvaliacao = new Avaliacao();
        newAvaliacao.setTipo(body.tipo());
        newAvaliacao.setNome(body.nome());
        newAvaliacao.setData(body.data());
        newAvaliacao.setPeso(body.peso());
        newAvaliacao.setDisciplina(disciplina);
        newAvaliacao.setProfessor(professor);
        newAvaliacao.setTurma(turma);
        newAvaliacao.setConcluida(false);

        Avaliacao avaliacao = repository.save(newAvaliacao);

        return convertToResponseDTO(avaliacao);
    }

    @Override
    public List<AvaliacaoResponseDTO> getByProfessorTurmaAndDisciplina(String idProfessor, Long idDisciplina, Long idTurma) {
        List<Avaliacao> avaliacoes = repository.findByTurmaIdTurmaAndProfessorIdProfessorAndDisciplinaIdDisciplina(
                idTurma,
                idProfessor,
                idDisciplina
        );




                System.out.println(avaliacoes);


        return avaliacoes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Set<AvaliacaoResponseDTO> getByAluno(String idAluno) {
        Aluno aluno = this.alunoRepository.findById(idAluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrada com ID: " + idAluno));

        Set<Avaliacao> avaliacoes = repository.findByAluno(idAluno);

        return avaliacoes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toSet());
    }

    private AvaliacaoResponseDTO convertToResponseDTO(Avaliacao avaliacao) {

        return new AvaliacaoResponseDTO(avaliacao);
    }

    public List<Avaliacao> seuMetodoDeBusca(Long idTurma, String idProfessor, Long idDisciplina) {

        Optional<Turma> turmaEncontrada = turmaRepository.findById(idTurma);
        if (turmaEncontrada.isPresent()) {
            System.out.println("   - Detalhes da Turma: ID=" + turmaEncontrada.get().getIdTurma() + ", Ativo=" + turmaEncontrada.get().getAtivo());
        }

        Optional<Disciplina> disciplinaEncontrada = disciplinaRepository.findById(idDisciplina);
        if (disciplinaEncontrada.isPresent()) {
            System.out.println("   - Detalhes da Disciplina: ID=" + disciplinaEncontrada.get().getIdDisciplina() + ", Status=" + disciplinaEncontrada.get().isStatus());
        }

        Optional<Professor> professorEncontrado = professorRepository.findById(idProfessor);
        
        List<Avaliacao> avaliacoes = repository.findByTurmaAndProfessorAndDisciplina(turmaEncontrada.get(), professorEncontrado.get(), disciplinaEncontrada.get());
        System.out.println("Resultado final da query: " + avaliacoes.size() + " avaliações encontradas.");

        return avaliacoes;
    }
}
