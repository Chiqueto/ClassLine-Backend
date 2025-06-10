package com.senai.classline.service.impl;

import com.senai.classline.domain.avaliacao.Avaliacao;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.avaliacao.AvaliacaoDTO;
import com.senai.classline.dto.avaliacao.AvaliacaoResponseDTO;
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.repositories.AvaliacaoRepository;
import com.senai.classline.repositories.DisciplinaRepository;
import com.senai.classline.repositories.ProfessorRepository;
import com.senai.classline.repositories.TurmaRepository;
import com.senai.classline.service.AvaliacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliacaoServiceImpl implements AvaliacaoService {
    final AvaliacaoRepository repository;
    final DisciplinaRepository disciplinaRepository;
    final ProfessorRepository professorRepository;
    final TurmaRepository turmaRepository;

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

    private AvaliacaoResponseDTO convertToResponseDTO(Avaliacao avaliacao) {

        return new AvaliacaoResponseDTO(avaliacao);
    }
}
